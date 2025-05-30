package com.shenriques.demo.projection.adapter.out.persistence;


import com.shenriques.demo.projection.domain.model.DeviceStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(DeviceStatusJpaAdapter.class) // wires in the adapter manually
class DeviceStatusJpaAdapterTest {

    @Autowired
    private DeviceStatusJpaAdapter adapter;

    @Autowired
    private DeviceStatusRepository repository;

    @Test
    void saveAndFindById_shouldPersistAndRetrieveDeviceStatus() {
        DeviceStatus status = new DeviceStatus(1, 10.5, LocalDateTime.parse("2025-04-06T16:00:00"));
        adapter.save(status);

        Optional<DeviceStatus> result = adapter.findById(1);

        assertThat(result).isPresent();
        assertThat(result.get().getDeviceId()).isEqualTo(1);
        assertThat(result.get().getMeasurement()).isEqualTo(10.5);
    }

    @Test
    void findAll_shouldReturnAllStatuses() {
        DeviceStatus one = new DeviceStatus(1, 10.5, LocalDateTime.parse("2025-04-06T16:00:00"));
        DeviceStatus two = new DeviceStatus(2, 11.2, LocalDateTime.parse("2025-04-06T16:00:00"));

        adapter.save(one);
        adapter.save(two);

        var all = adapter.findAll();

        assertThat(all).hasSize(2);
        assertThat(all).extracting(DeviceStatus::getDeviceId).containsExactlyInAnyOrder(1, 2);
    }
}