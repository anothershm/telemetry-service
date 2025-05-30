package com.shenriques.demo.projection.application;


import com.shenriques.demo.common.event.TelemetryRecordedEvent;
import com.shenriques.demo.projection.domain.model.DeviceStatus;
import com.shenriques.demo.projection.domain.port.out.DeviceStatusPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeviceStatusProjectionUpdaterTest {

    private DeviceStatusPersistencePort persistencePort;
    private DeviceStatusProjectionUpdater updater;

    @BeforeEach
    void setUp() {
        persistencePort = mock(DeviceStatusPersistencePort.class);
        updater = new DeviceStatusProjectionUpdater(persistencePort);
    }

    @Test
    void handle_shouldUpdateExistingDeviceStatus_whenExistsAndItsNewer() {
        var event = new TelemetryRecordedEvent(1, 23.5, LocalDateTime.parse("2025-04-06T16:01:00"));
        var existing = spy(new DeviceStatus(1, 20.0, LocalDateTime.parse("2025-04-06T16:00:00")));

        when(persistencePort.findById(1)).thenReturn(Optional.of(existing));

        updater.handle(event);

        verify(existing).updateIfNewer(23.5, LocalDateTime.parse("2025-04-06T16:01:00"));
        verify(persistencePort).save(existing);
    }

    @Test
    void handle_shouldCreateNewDeviceStatus_whenNotExists() {
        var event = new TelemetryRecordedEvent(2, 18.2, LocalDateTime.parse("2025-04-06T16:00:00"));

        when(persistencePort.findById(2)).thenReturn(Optional.empty());

        updater.handle(event);

        verify(persistencePort).save(argThat(ds ->
                ds.getDeviceId().equals(2) &&
                        ds.getMeasurement().equals(18.2) &&
                        ds.getDate().equals(LocalDateTime.parse("2025-04-06T16:00:00"))
        ));
    }

    @Test
    void getAll_shouldReturnAllDeviceStatuses() {
        var status1 = new DeviceStatus(1, 21.0, LocalDateTime.parse("2025-04-06T16:00:00"));
        var status2 = new DeviceStatus(2, 19.5, LocalDateTime.parse("2025-04-06T16:00:00"));

        when(persistencePort.findAll()).thenReturn(List.of(status1, status2));

        var result = updater.getAll();

        assertThat(result).containsExactly(status1, status2);
        verify(persistencePort).findAll();
    }

    @Test
    void handle_shouldNotUpdateWhenEventIsOlder() {
        var event = new TelemetryRecordedEvent(1, 25.0, LocalDateTime.parse("2025-04-06T15:00:00"));
        var existing = spy(new DeviceStatus(1, 26.0, LocalDateTime.parse("2025-04-06T16:00:00")));

        when(persistencePort.findById(1)).thenReturn(Optional.of(existing));

        updater.handle(event);

        verify(existing).updateIfNewer(25.0, LocalDateTime.parse("2025-04-06T15:00:00"));
        verify(persistencePort, never()).save(existing);
    }

}
