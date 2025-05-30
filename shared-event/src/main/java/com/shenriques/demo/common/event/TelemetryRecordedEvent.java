package com.shenriques.demo.common.event;
import java.time.LocalDateTime;


public record TelemetryRecordedEvent(
        int deviceId,
        double measurement,
        LocalDateTime date
) {
}
