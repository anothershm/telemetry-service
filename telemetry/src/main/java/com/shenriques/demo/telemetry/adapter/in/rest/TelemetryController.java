package com.shenriques.demo.telemetry.adapter.in.rest;


import com.shenriques.demo.telemetry.adapter.in.rest.mapper.TelemetryCommandMapper;
import com.shenriques.demo.telemetry.application.handler.RecordTelemetryHandler;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/telemetry")
public class TelemetryController {

    private final RecordTelemetryHandler recordTelemetryHandler;

    public TelemetryController(RecordTelemetryHandler recordTelemetryHandler) {
        this.recordTelemetryHandler = recordTelemetryHandler;
    }

    @PostMapping
    public ResponseEntity<Void> recordTelemetry(@Valid @RequestBody TelemetryDTO dto) {
        recordTelemetryHandler.record(TelemetryCommandMapper.fromDTO(dto));
        return ResponseEntity.status(201).build();
    }
}
