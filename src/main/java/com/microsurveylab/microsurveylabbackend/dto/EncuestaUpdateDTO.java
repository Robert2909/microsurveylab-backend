package com.microsurveylab.microsurveylabbackend.dto;

/**
 * DTO para actualizar una encuesta.
 *
 * Se usa como actualización parcial:
 * - si un campo viene null, se ignora
 * - si viene con valor, se actualiza
 */
public class EncuestaUpdateDTO {
    private String pregunta;
    private String descripcion;
    private Boolean activa;

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

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }
}
