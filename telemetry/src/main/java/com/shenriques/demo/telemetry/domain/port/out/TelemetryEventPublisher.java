package com.shenriques.demo.telemetry.domain.port.out;


import com.shenriques.demo.common.event.TelemetryRecordedEvent;

public interface TelemetryEventPublisher {
    void publish(TelemetryRecordedEvent event);
}
