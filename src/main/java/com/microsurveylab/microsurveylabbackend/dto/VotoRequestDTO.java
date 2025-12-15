package com.microsurveylab.microsurveylabbackend.dto;

import jakarta.validation.constraints.NotNull;

/**
 * DTO que representa la información mínima para registrar un voto.
 *
 * El frontend solo manda el id de la opción seleccionada.
 * La encuesta se obtiene desde la URL.
 */
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
