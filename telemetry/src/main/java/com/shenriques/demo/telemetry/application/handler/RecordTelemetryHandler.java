package com.shenriques.demo.telemetry.application.handler;

import com.shenriques.demo.common.event.TelemetryRecordedEvent;
import com.shenriques.demo.telemetry.domain.model.RecordTelemetryCommand;
import com.shenriques.demo.telemetry.domain.model.Telemetry;
import com.shenriques.demo.telemetry.domain.port.in.RecordTelemetryUseCase;
import com.shenriques.demo.telemetry.domain.port.out.TelemetryEventPublisher;
import com.shenriques.demo.telemetry.domain.port.out.TelemetryPersistencePort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RecordTelemetryHandler implements RecordTelemetryUseCase {

    private final TelemetryPersistencePort persistencePort;
    private final TelemetryEventPublisher eventPublisher;

    public RecordTelemetryHandler(TelemetryPersistencePort persistencePort, TelemetryEventPublisher eventPublisher) {
        this.persistencePort = persistencePort;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void record(RecordTelemetryCommand command) {
        Telemetry telemetry = new Telemetry(
                command.deviceId(),
                command.measurement(),
                command.date()
        );
        persistencePort.save(telemetry);

        eventPublisher.publish(new TelemetryRecordedEvent(
                telemetry.getDeviceId(),
                telemetry.getMeasurement(),
                telemetry.getDate()
        ));
    }
}
