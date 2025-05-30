package com.shenriques.demo.projection.application;

import com.shenriques.demo.common.event.TelemetryRecordedEvent;
import com.shenriques.demo.projection.domain.model.DeviceStatus;
import com.shenriques.demo.projection.domain.port.out.DeviceStatusPersistencePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceStatusProjectionUpdater {

    private final DeviceStatusPersistencePort persistencePort;

    @Transactional
    public void handle(TelemetryRecordedEvent event) {
        persistencePort.findById(event.deviceId()).ifPresentOrElse(
                existing -> {
                    if (existing.updateIfNewer(event.measurement(), event.date())) {
                        persistencePort.save(existing);
                    }
                },
                () -> {
                    DeviceStatus newStatus = new DeviceStatus(
                            event.deviceId(),
                            event.measurement(),
                            event.date()
                    );
                    persistencePort.save(newStatus);
                }
        );
    }

    public List<DeviceStatus> getAll() {
        return persistencePort.findAll();
    }
}