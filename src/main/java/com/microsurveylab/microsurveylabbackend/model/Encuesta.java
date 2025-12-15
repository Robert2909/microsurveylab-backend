package com.microsurveylab.microsurveylabbackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa una encuesta.
 *
 * Contiene la información principal y mantiene la relación
 * con sus opciones de respuesta.
 */
@Entity
public class Encuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pregunta;

    private String descripcion;

    // Indica si la encuesta está abierta para recibir votos.
    private Boolean activa = true;

    // Se asigna automáticamente al crear la encuesta.
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    /**
     * Relación uno a muchos con Opcion.
     *
     * - cascade ALL: al guardar o borrar la encuesta se afectan sus opciones
     * - orphanRemoval: evita dejar opciones sueltas si se eliminan
     */
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

    // Métodos de ayuda para mantener la relación sincronizada.
    public void addOpcion(Opcion opcion) {
        opciones.add(opcion);
        opcion.setEncuesta(this);
    }
    
    /*
     * Me di cuenta de que estos dos métodos nunca se usan, pero quedaron aquí por si acaso.
     * Aqui trabajé de a gratis :c
     * Es mala práctica tener código muerto, pero bueno...
     */
    public void removeOpcion(Opcion opcion) {
        opciones.remove(opcion);
        opcion.setEncuesta(null);
    }
}
