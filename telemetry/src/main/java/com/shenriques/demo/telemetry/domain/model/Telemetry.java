package com.shenriques.demo.telemetry.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class Telemetry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int deviceId;
    private double measurement;
    private LocalDateTime date;

    public Telemetry(int deviceId, double measurement, LocalDateTime date) {
        this.deviceId = deviceId;
        this.measurement = measurement;
        this.date = date;

    }

}