package com.microsurveylab.microsurveylabbackend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class EncuestaResponseDTO {

    private Long id;
    private String pregunta;
    private String descripcion;
    private Boolean activa;
    private LocalDateTime fechaCreacion;
    private List<OpcionRespuestaDTO> opciones;

    public static class OpcionRespuestaDTO {
        private Long id;
        private String texto;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTexto() {
            return texto;
        }

        public void setTexto(String texto) {
            this.texto = texto;
        }
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<OpcionRespuestaDTO> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<OpcionRespuestaDTO> opciones) {
        this.opciones = opciones;
    }
}
