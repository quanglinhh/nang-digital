package com.nang.reportservice.controller;

import com.nang.reportservice.dto.request.ReportRequest;
import com.nang.reportservice.dto.response.ApiResponse;
import com.nang.reportservice.dto.response.TpsResponse;
import com.nang.reportservice.service.TpsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tps")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TpsController {
    TpsService tpsService;

    @PostMapping()
    public ApiResponse<List<TpsResponse>> getTpsResponse(@RequestBody ReportRequest request){
        return ApiResponse.<List<TpsResponse>>builder()
                .result(tpsService.getTpsByPeriod(request.getPeriod())).build();
    }
}
