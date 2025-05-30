package com.shenriques.demo.projection.adapter.out.rest;

import com.shenriques.demo.projection.domain.model.DeviceStatus;

import java.time.LocalDateTime;

public record DeviceStatusDTO(Integer deviceId, Double measurement, LocalDateTime date) {
    public static DeviceStatusDTO from(DeviceStatus ds) {
        return new DeviceStatusDTO(ds.getDeviceId(), ds.getMeasurement(), ds.getDate());
    }
}
