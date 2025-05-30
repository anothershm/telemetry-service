package com.shenriques.demo.projection.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Slf4j
public class DeviceStatus {

    @Id
    private Integer deviceId;

    private Double measurement;

    private LocalDateTime date;

    public DeviceStatus(Integer deviceId, Double measurement, LocalDateTime date) {
        this.deviceId = deviceId;
        this.measurement = measurement;
        this.date = date;
    }

    public boolean updateIfNewer(Double measurement, LocalDateTime date) {
        if (this.date.isBefore(date)) {
            this.measurement = measurement;
            this.date = date;
            return true;
        }
        return false;
    }
}