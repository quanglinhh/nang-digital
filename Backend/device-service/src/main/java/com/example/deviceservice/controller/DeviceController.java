package com.example.deviceservice.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.deviceservice.dto.request.CreateDeviceRequest;
import com.example.deviceservice.dto.response.ApiResponse;
import com.example.deviceservice.dto.response.DeviceResponse;
import com.example.deviceservice.service.DeviceService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DeviceController {

    DeviceService deviceService;

    @PostMapping("/create")
    ApiResponse<DeviceResponse> createUser(@RequestBody @Valid CreateDeviceRequest request) {
        return ApiResponse.<DeviceResponse>builder()
                .result(deviceService.createDevice(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<DeviceResponse>> getAllDevices() {
        return ApiResponse.<List<DeviceResponse>>builder()
                .result(deviceService.getAllDevices())
                .build();
    }
}
