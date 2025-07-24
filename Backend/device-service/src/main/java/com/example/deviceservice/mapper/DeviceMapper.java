package com.example.deviceservice.mapper;

import org.mapstruct.Mapper;

import com.example.deviceservice.dto.request.CreateDeviceRequest;
import com.example.deviceservice.dto.response.DeviceResponse;
import com.example.deviceservice.entity.Device;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    Device deviceRequestToDevice(CreateDeviceRequest request);

    DeviceResponse todDeviceResponse(Device device);
}
