package com.shenriques.demo.projection.adapter.in.event;

import com.shenriques.demo.projection.application.DeviceStatusProjectionUpdater;
import com.shenriques.demo.common.event.TelemetryRecordedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaTelemetryEventListener {

    private final DeviceStatusProjectionUpdater updater;

    public KafkaTelemetryEventListener(DeviceStatusProjectionUpdater updater) {
        this.updater = updater;
    }

    @KafkaListener(topics = "telemetry-recorded", groupId = "telemetry-consumers")
    public void listen(TelemetryRecordedEvent event) {
        log.info("Received event from Kafka: {}", event);
        updater.handle(event);
    }
}
