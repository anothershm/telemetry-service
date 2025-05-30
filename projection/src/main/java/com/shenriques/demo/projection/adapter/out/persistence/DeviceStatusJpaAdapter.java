package com.shenriques.demo.projection.adapter.out.persistence;


import com.shenriques.demo.projection.domain.model.DeviceStatus;
import com.shenriques.demo.projection.domain.port.out.DeviceStatusPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DeviceStatusJpaAdapter implements DeviceStatusPersistencePort {

    private final DeviceStatusRepository repository;

    @Override
    public void save(DeviceStatus deviceStatus) {
        repository.save(deviceStatus);
    }

    @Override
    public Optional<DeviceStatus> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<DeviceStatus> findAll() {
        return repository.findAll();
    }
}