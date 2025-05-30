package com.shenriques.demo.telemetry.adapter.out.persistence;

import com.shenriques.demo.telemetry.domain.model.Telemetry;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

class TelemetryJpaAdapterTest {

    private final TelemetryRepository telemetryRepository = mock(TelemetryRepository.class);
    private final TelemetryJpaAdapter adapter = new TelemetryJpaAdapter(telemetryRepository);

    @Test
    void save_shouldDelegateToRepository() {
        Telemetry telemetry = new Telemetry(1, 23, LocalDateTime.parse("2025-04-06T16:00:00"));

        adapter.save(telemetry);

        verify(telemetryRepository).save(telemetry);
    }

    @Test
    void save_givenNull_shouldNotCallRepository() {
        adapter.save(null);

        verifyNoInteractions(telemetryRepository);
    }
}