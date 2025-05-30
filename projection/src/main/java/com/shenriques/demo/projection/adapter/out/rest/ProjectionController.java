package com.shenriques.demo.projection.adapter.out.rest;

import com.shenriques.demo.projection.application.DeviceStatusProjectionUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/device-status")
@RequiredArgsConstructor
public class ProjectionController {

    private final DeviceStatusProjectionUpdater updater;

    @GetMapping
    public ResponseEntity<List<DeviceStatusDTO>> getAll() {
        return ResponseEntity.ok(
                updater.getAll().stream().map(DeviceStatusDTO::from).toList()
        );
    }

}