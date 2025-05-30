package com.shenriques.demo.telemetry.adapter.in.rest;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TelemetryDTO {

    @Min(1)
    @NotNull
    private Integer deviceId;

    private @Min(-100)
    @Max(100)
    @NotNull Double measurement;

    @NotNull
    private LocalDateTime date;

}
