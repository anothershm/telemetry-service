package com.shenriques.demo.telemetry.adapter.out.event;

import com.shenriques.demo.common.event.TelemetryRecordedEvent;
import com.shenriques.demo.telemetry.domain.port.out.TelemetryEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaTelemetryEventPublisher implements TelemetryEventPublisher {

    private final KafkaTemplate<String, TelemetryRecordedEvent> kafkaTemplate;

    public KafkaTelemetryEventPublisher(KafkaTemplate<String, TelemetryRecordedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(TelemetryRecordedEvent event) {
        if (event == null) return;
        kafkaTemplate.send("telemetry-recorded", String.valueOf(event.deviceId()), event);
    }
}