package com.microsurveylab.microsurveylabbackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Encuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pregunta;

    private String descripcion;

    private Boolean activa = true;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @OneToMany(mappedBy = "encuesta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Opcion> opciones = new ArrayList<>();

    public Encuesta() { }

    public Long getId() {
        return id;
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

    public List<Opcion> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<Opcion> opciones) {
        this.opciones = opciones;
    }

    public void addOpcion(Opcion opcion) {
        opciones.add(opcion);
        opcion.setEncuesta(this);
    }

    public void removeOpcion(Opcion opcion) {
        opciones.remove(opcion);
        opcion.setEncuesta(null);
    }
}
