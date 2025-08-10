package com.nang.reportservice.mapper;

import com.nang.reportservice.dto.TpsMessage;
import com.nang.reportservice.dto.response.TpsResponse;
import com.nang.reportservice.entity.TpsReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TpsMapper {
    @Mapping(source = "timestamp", target = "timestamp")
    @Mapping(source = "tps", target = "tps")
    TpsResponse tpsMessageToTpsResponse(TpsMessage tpsMessage);

    @Mapping(source = "reportTime", target = "timestamp")
    @Mapping(source = "amount", target = "tps")
    TpsResponse TpsReportToTpsResponse(TpsReport tpsReport);
}
