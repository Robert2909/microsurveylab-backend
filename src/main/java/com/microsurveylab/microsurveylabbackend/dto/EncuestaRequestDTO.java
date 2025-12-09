package com.microsurveylab.microsurveylabbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public class EncuestaRequestDTO {

    @NotBlank
    private String pregunta;

    private String descripcion;

    @Size(min = 2, message = "La encuesta debe tener al menos 2 opciones")
    private List<@NotBlank String> opciones;

    // Getters y setters

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }
}
