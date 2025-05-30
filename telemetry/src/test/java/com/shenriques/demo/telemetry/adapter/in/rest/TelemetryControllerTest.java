package com.shenriques.demo.telemetry.adapter.in.rest;

import com.shenriques.demo.telemetry.application.handler.RecordTelemetryHandler;
import com.shenriques.demo.telemetry.domain.model.RecordTelemetryCommand;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class TelemetryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RecordTelemetryHandler recordTelemetryHandler;

    @Test
    void whenValidTelemetry_thenReturnsOk() throws Exception {
        mockMvc.perform(post("/telemetry")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "deviceId": 2,
                            "measurement": 8,
                            "date": "2025-01-31T13:00:01"
                        }
                        """)
        ).andExpect(status().isCreated());


        Mockito.verify(recordTelemetryHandler).record(Mockito.any(RecordTelemetryCommand.class));
    }

    @Test
    void whenInvalidTelemetry_thenReturnsBadRequest() throws Exception {

        mockMvc.perform(post("/telemetry")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "deviceId": 2,
                            "date": "2025-01-31T13:00:01"
                        }
                        """)
        ).andExpect(status().isBadRequest());

        Mockito.verifyNoInteractions(recordTelemetryHandler);
    }
}
