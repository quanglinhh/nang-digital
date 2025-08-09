package com.nang.reportservice.schdeule;

import com.nang.reportservice.entity.TpsReport;
import com.nang.reportservice.enumm.ReportType;
import com.nang.reportservice.repository.TpsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

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
        String lockKey = "LOCK_TPS_" + type;
        String lockValue = UUID.randomUUID().toString();
        boolean acquired = Boolean.TRUE.equals(
                redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, Duration.ofMinutes(1)) // lock timeout
        );

        if (!acquired) {
            log.info("Skip {} job because another instance is processing", type);
            return; // có lock => job khác đang chạy
        }

        try {
            LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
            log.info("start save tps to DB type: {}", type);

            TpsReport lastReport = tpsRepository.findTopByReportTypeOrderByReportTimeDesc(type);
            LocalDateTime lastTime = (lastReport != null)
                    ? lastReport.getReportTime()
                    : getDefaultStartTime(now, unit);

            LocalDateTime start = lastTime.plus(1, unit);
            LocalDateTime end = now.minus(1, unit);

            for (LocalDateTime time = start; !time.isAfter(end); time = time.plus(1, unit)) {
                String redisKey = String.format("%s_%s", redisKeyPrefix, time.format(formatter));
                String value = redisTemplate.opsForValue().get(redisKey);

                long amount = (value != null) ? Long.parseLong(value) : 0;

                TpsReport report = new TpsReport();
                report.setReportTime(time);
                report.setReportType(type);
                report.setAmount(amount);
                tpsRepository.save(report);

                redisTemplate.delete(redisKey);

                log.info("Saved {} report: {} -> {} and deleted key", type, redisKey, amount);
            }
        } finally {
            // Chỉ xóa lock nếu chính mình đặt
            String currentValue = redisTemplate.opsForValue().get(lockKey);
            if (lockValue.equals(currentValue)) {
                redisTemplate.delete(lockKey);
            }
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
