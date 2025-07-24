package com.example.deviceservice.service;

import java.util.List;

import com.example.deviceservice.dto.request.CreateDeviceRequest;
import com.example.deviceservice.dto.response.DeviceResponse;

public interface DeviceService {
    DeviceResponse createDevice(CreateDeviceRequest request);

    DeviceResponse getDeviceById(String id);

    List<DeviceResponse> getAllDevices();
}
