package com.shenriques.demo.projection.domain.port.out;

import com.shenriques.demo.projection.domain.model.DeviceStatus;

import java.util.List;
import java.util.Optional;

public interface DeviceStatusPersistencePort {
    void save(DeviceStatus deviceStatus);

    Optional<DeviceStatus> findById(Integer id);

    List<DeviceStatus> findAll();
}