package com.shenriques.demo.telemetry.adapter.out.persistence;

import com.shenriques.demo.telemetry.domain.model.Telemetry;
import com.shenriques.demo.telemetry.domain.port.out.TelemetryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TelemetryJpaAdapter implements TelemetryPersistencePort {

    private final TelemetryRepository telemetryRepository;

    @Override
    public void save(Telemetry telemetry) {
        if (telemetry == null) return;
        telemetryRepository.save(telemetry);
    }
}
