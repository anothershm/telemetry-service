package com.shenriques.demo.telemetry.domain.model;


import java.time.LocalDateTime;

public record RecordTelemetryCommand(
        int deviceId,
        double measurement,
        LocalDateTime date
) {
}