package com.shenriques.demo.telemetry.application.handler;

import com.shenriques.demo.common.event.TelemetryRecordedEvent;
import com.shenriques.demo.telemetry.domain.model.RecordTelemetryCommand;
import com.shenriques.demo.telemetry.domain.model.Telemetry;
import com.shenriques.demo.telemetry.domain.port.out.TelemetryEventPublisher;
import com.shenriques.demo.telemetry.domain.port.out.TelemetryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class RecordTelemetryHandlerTest {

    @Mock
    private TelemetryPersistencePort persistencePort;

    @Mock
    private TelemetryEventPublisher eventPublisher;

    @InjectMocks
    private RecordTelemetryHandler handler;

    private RecordTelemetryCommand command;

    @BeforeEach
    void setup() {
        command = new RecordTelemetryCommand(42, 27, LocalDateTime.now());
    }

    @Test
    void record_shouldPersistTelemetryAndPublishEvent() {
        Telemetry expectedTelemetry = new Telemetry(command.deviceId(), command.measurement(), command.date());
        TelemetryRecordedEvent expectedEvent = new TelemetryRecordedEvent(command.deviceId(), command.measurement(), command.date());

        handler.record(command);

        verify(persistencePort).save(expectedTelemetry);
        verify(eventPublisher).publish(expectedEvent);

    }

    @Test
    void record_shouldNotPublish_whenPersistenceFails() {
        doThrow(new RuntimeException("DB error")).when(persistencePort).save(any());

        assertThatThrownBy(() -> handler.record(command)).isInstanceOf(RuntimeException.class);

        verifyNoInteractions(eventPublisher);
    }


}