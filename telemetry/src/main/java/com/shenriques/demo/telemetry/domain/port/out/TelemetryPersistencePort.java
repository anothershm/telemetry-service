package com.shenriques.demo.telemetry.domain.port.out;


import com.shenriques.demo.telemetry.domain.model.Telemetry;

public interface TelemetryPersistencePort {
    void save(Telemetry telemetry);
}