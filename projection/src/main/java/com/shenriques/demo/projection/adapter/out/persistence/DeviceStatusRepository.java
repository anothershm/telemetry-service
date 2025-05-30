package com.shenriques.demo.projection.adapter.out.persistence;

import com.shenriques.demo.projection.domain.model.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceStatusRepository extends JpaRepository<DeviceStatus, Integer> {
}
