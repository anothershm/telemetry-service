package com.shenriques.demo.telemetry.adapter.out.persistence;

import com.shenriques.demo.telemetry.domain.model.Telemetry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelemetryRepository extends JpaRepository<Telemetry, Long> {
}
