package com.microsurveylab.microsurveylabbackend.dto;

import jakarta.validation.constraints.NotNull;

public class VotoRequestDTO {

    @NotNull
    private Long opcionId;

    public Long getOpcionId() {
        return opcionId;
    }

    public void setOpcionId(Long opcionId) {
        this.opcionId = opcionId;
    }
}
