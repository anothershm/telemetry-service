package com.shenriques.demo.telemetry.adapter.in.rest.mapper;

import com.shenriques.demo.telemetry.adapter.in.rest.TelemetryDTO;
import com.shenriques.demo.telemetry.domain.model.RecordTelemetryCommand;

public class TelemetryCommandMapper {

    public static RecordTelemetryCommand fromDTO(TelemetryDTO telemetry) {
        return new RecordTelemetryCommand(telemetry.getDeviceId(), telemetry.getMeasurement(), telemetry.getDate());
    }

}
