package com.shenriques.demo.telemetry.adapter.out.event;

import com.shenriques.demo.common.event.TelemetryRecordedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;


class KafkaTelemetryEventPublisherTest {

    private final KafkaTemplate<String, TelemetryRecordedEvent> kafkaTemplate = mock(KafkaTemplate.class);
    private KafkaTelemetryEventPublisher publisher;

    @BeforeEach
    void setUp() {
        publisher = new KafkaTelemetryEventPublisher(kafkaTemplate);
    }

    @Test
    void publish_sendsEventToCorrectTopic() {
        TelemetryRecordedEvent event = new TelemetryRecordedEvent(42, 15, LocalDateTime.parse("2025-04-06T16:00:00"));
        publisher.publish(event);

        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<TelemetryRecordedEvent> valueCaptor = ArgumentCaptor.forClass(TelemetryRecordedEvent.class);

        verify(kafkaTemplate).send(eq("telemetry-recorded"), keyCaptor.capture(), valueCaptor.capture());

        assertThat(keyCaptor.getValue()).isEqualTo("42");
        assertThat(valueCaptor.getValue()).isEqualTo(event);
    }

    @Test
    void publish_nullEvent_shouldNotSendAnything() {
        publisher.publish(null);

        verifyNoInteractions(kafkaTemplate);
    }

    @Test
    void publish_whenKafkaFails_shouldPropagateException() {
        TelemetryRecordedEvent event = new TelemetryRecordedEvent(42, 20, LocalDateTime.parse("2025-04-06T16:00:00"));
        doThrow(new RuntimeException("Kafka down")).when(kafkaTemplate)
                .send(any(), any(), any());

        // you can either assert exception is propagated...
        assertThatThrownBy(() -> publisher.publish(event))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Kafka down");
    }

}