package com.example.deviceservice.service;

import java.util.List;

import com.example.deviceservice.exception.AppException;
import com.example.deviceservice.exception.ErrorCode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.deviceservice.dto.request.CreateDeviceRequest;
import com.example.deviceservice.dto.response.DeviceResponse;
import com.example.deviceservice.entity.Device;
import com.example.deviceservice.mapper.DeviceMapper;
import com.example.deviceservice.repository.DeviceRepository;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceServiceImpl implements DeviceService {
    DeviceRepository deviceRepository;
    DeviceMapper deviceMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public DeviceResponse createDevice(CreateDeviceRequest request) {
        if(deviceRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.DUPLICATE_DEVICE_NAME);
        }
        var device = new Device();
        device.setName(request.getName());
        device = deviceRepository.save(device);
        return deviceMapper.todDeviceResponse(device);
    }

    @Override
    public DeviceResponse getDeviceById(String id) {
        var device = deviceRepository.findById(id).orElse(null);
        if (device == null) {
            return null;
        }
        return deviceMapper.todDeviceResponse(device);
    }

    @Override
    public List<DeviceResponse> getAllDevices() {
        var deviceList = deviceRepository.findAll();
        return deviceList.stream()
                .map(device -> {
                    return deviceMapper.todDeviceResponse(device);
                })
                .toList();
    }
}
