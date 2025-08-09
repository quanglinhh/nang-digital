package com.nang.reportservice.schdeule;

import com.nang.reportservice.entity.TpsReport;
import com.nang.reportservice.enumm.ReportType;
import com.nang.reportservice.repository.TpsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class TpsSchedule {

    private final RedisTemplate<String, String> redisTemplate;
    private final TpsRepository tpsRepository;

    @Scheduled(cron = "0 * * * * *") // chạy mỗi đầu phút
    public void processMinutes() {
        processReport(
                ReportType.MINUTE,
                "TPS:minutes",
                DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm"),
                ChronoUnit.MINUTES
        );
    }

    @Scheduled(cron = "0 0 * * * *") // chạy mỗi đầu giờ
    public void processHours() {
        processReport(
                ReportType.HOUR,
                "TPS:hours",
                DateTimeFormatter.ofPattern("yyyy-MM-dd_HH"),
                ChronoUnit.HOURS
        );
    }

    @Scheduled(cron = "0 0 0 * * *") // chạy mỗi đầu ngày
    public void processDays() {
        processReport(
                ReportType.DAY,
                "TPS:days",
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                ChronoUnit.DAYS
        );
    }


    private void processReport(ReportType type, String redisKeyPrefix, DateTimeFormatter formatter, ChronoUnit unit) {
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        log.info("start save tps to DB type: {}", type);
        // Lấy bản ghi gần nhất theo loại report
        TpsReport lastReport = tpsRepository.findTopByReportTypeOrderByReportTimeDesc(type);
        LocalDateTime lastTime = (lastReport != null)
                ? lastReport.getReportTime()
                : getDefaultStartTime(now, unit);

        // Loop từ thời gian kế tiếp đến thời gian hiện tại - 1
        LocalDateTime start = lastTime.plus(1, unit);
        LocalDateTime end = now.minus(1, unit);

        for (LocalDateTime time = start; !time.isAfter(end); time = time.plus(1, unit)) {
            String redisKey = String.format("%s_%s", redisKeyPrefix, time.format(formatter));
            log.info("save tps to DB: {}", redisKey);
            String value = redisTemplate.opsForValue().get(redisKey);

            long amount = (value != null) ? Long.parseLong(value) : 0;

            // Lưu vào DB
            TpsReport report = new TpsReport();
            report.setReportTime(time);
            report.setReportType(type);
            report.setAmount(amount);
            tpsRepository.save(report);

            // Xóa key Redis
            redisTemplate.delete(redisKey);

            log.info("Saved {} report: {} -> {} and deleted key", type, redisKey, amount);
        }
    }

    private LocalDateTime getDefaultStartTime(LocalDateTime now, ChronoUnit unit) {
        switch (unit) {
            case MINUTES:
                return now.minusMinutes(10);
            case HOURS:
                return now.minusHours(24);
            case DAYS:
                return now.minusDays(7);
            default:
                throw new IllegalArgumentException("Unsupported time unit: " + unit);
        }
    }
}
