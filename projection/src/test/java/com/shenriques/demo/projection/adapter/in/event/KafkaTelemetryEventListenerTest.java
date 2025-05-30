package com.shenriques.demo.projection.adapter.in.event;

import com.shenriques.demo.common.event.TelemetryRecordedEvent;
import com.shenriques.demo.projection.application.DeviceStatusProjectionUpdater;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


class KafkaTelemetryEventListenerTest {

    private final DeviceStatusProjectionUpdater updater = mock(DeviceStatusProjectionUpdater.class);
    private final KafkaTelemetryEventListener listener = new KafkaTelemetryEventListener(updater);

    @Test
    void listen_shouldDelegateToUpdater() {
        TelemetryRecordedEvent event = new TelemetryRecordedEvent(1, 10, LocalDateTime.parse("2025-04-06T16:00:00"));

        listener.listen(event);

        verify(updater).handle(event);
    }
}