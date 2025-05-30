package com.shenriques.demo.telemetry.domain.port.in;


import com.shenriques.demo.telemetry.domain.model.RecordTelemetryCommand;

public interface RecordTelemetryUseCase {
    void record(RecordTelemetryCommand telemetry);
}