package com.shenriques.demo.projection;

import com.shenriques.demo.projection.application.DeviceStatusProjectionUpdater;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
class ProjectionApplicationTest {

    @Autowired
    private DeviceStatusProjectionUpdater updater;

    @Test
    void contextLoads() {
        assertThat(updater).isNotNull();
    }
}