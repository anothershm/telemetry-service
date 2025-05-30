package com.shenriques.demo.telemetry;


import com.shenriques.demo.telemetry.adapter.in.rest.TelemetryController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class TelemetryApplicationTest {


    @Autowired
    private TelemetryController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }
}