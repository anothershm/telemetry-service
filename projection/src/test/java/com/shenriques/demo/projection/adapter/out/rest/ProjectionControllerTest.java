package com.shenriques.demo.projection.adapter.out.rest;


import com.shenriques.demo.projection.application.DeviceStatusProjectionUpdater;
import com.shenriques.demo.projection.domain.model.DeviceStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class ProjectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DeviceStatusProjectionUpdater updater;

    @Test
    void getAll_shouldReturnDeviceStatuses() throws Exception {
        List<DeviceStatus> statuses = List.of(
                new DeviceStatus(1, 22.5, LocalDateTime.parse("2025-04-06T12:00:00")),
                new DeviceStatus(2, 19.3, LocalDateTime.parse("2025-04-06T13:00:00"))
        );

        when(updater.getAll()).thenReturn(statuses);

        mockMvc.perform(get("/device-status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].deviceId").value(1))
                .andExpect(jsonPath("$[0].measurement").value(22.5))
                .andExpect(jsonPath("$[0].date").value("2025-04-06T12:00:00"))
                .andExpect(jsonPath("$[1].deviceId").value(2))
                .andExpect(jsonPath("$[1].measurement").value(19.3))
                .andExpect(jsonPath("$[1].date").value("2025-04-06T13:00:00"));
    }
}
