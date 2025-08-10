package com.nang.reportservice.service.impl;

import com.nang.reportservice.dto.TpsMessage;
import com.nang.reportservice.dto.response.TpsResponse;
import com.nang.reportservice.entity.TpsReport;
import com.nang.reportservice.enumm.ReportType;
import com.nang.reportservice.enumm.TpsPeriod;
import com.nang.reportservice.exception.AppException;
import com.nang.reportservice.exception.ErrorCode;
import com.nang.reportservice.mapper.TpsMapper;
import com.nang.reportservice.repository.TpsRepository;
import com.nang.reportservice.service.RedisService;
import com.nang.reportservice.service.TpsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Service
public class TpsServiceImpl implements TpsService {
    TpsRepository tpsRepository;
    RedisService redisService;
    TpsMapper tpsMapper;

    @Override
    public List<TpsResponse> getTpsByPeriod(TpsPeriod period) {
        switch (period) {
            case NEAREST_10_MINUTES :
                return getTpsNearest10Minutes();
            case FROM_START_OF_DAY:
                return getToday();
            case LAST_5_DAYS:
                return getLast5Days();
            default:
                throw new AppException(ErrorCode.INVALID_PERIOD);
        }
    }

    public List<TpsResponse> getTpsNearest10Minutes() {
        List<TpsMessage> tpsMessages = redisService.getRealtimeTpsData();
        return tpsMessages.stream().map(tpsMapper::tpsMessageToTpsResponse).toList();
    }

    public List<TpsResponse> getToday() {
        LocalDateTime startTime = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endTime = LocalDateTime.now();
        List<TpsReport> tpsReports = tpsRepository.findByTime(startTime, endTime, ReportType.HOUR);
        return tpsReports.stream().map(tpsMapper::TpsReportToTpsResponse).toList();
    }

    public List<TpsResponse> getLast5Days() {
        LocalDateTime startTime = LocalDate.now().minusDays(5).atStartOfDay();
        LocalDateTime endTime = LocalDate.now().atStartOfDay();
        List<TpsReport> tpsReports = tpsRepository.findByTime(startTime, endTime, ReportType.DAY);
        return tpsReports.stream()
                .map(tpsMapper::TpsReportToTpsResponse)
                .toList();
    }

}
