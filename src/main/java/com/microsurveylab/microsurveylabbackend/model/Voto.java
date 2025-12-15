package com.microsurveylab.microsurveylabbackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad que representa un voto.
 *
 * Un voto siempre está ligado a:
 * - una encuesta
 * - una opción
 */
@Entity
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Encuesta a la que pertenece el voto.
     * Relación many-to-one porque una encuesta tiene muchos votos.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encuesta_id")
    private Encuesta encuesta;

    /**
     * Opción seleccionada por el voto.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opcion_id")
    private Opcion opcion;

    // Se asigna automáticamente al registrar el voto.
    private LocalDateTime fechaVoto = LocalDateTime.now();

    public Voto() { }

    public Long getId() {
        return id;
    }

    public Encuesta getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(Encuesta encuesta) {
        this.encuesta = encuesta;
    }

    public Opcion getOpcion() {
        return opcion;
    }

    public void setOpcion(Opcion opcion) {
        this.opcion = opcion;
    }

    public LocalDateTime getFechaVoto() {
        return fechaVoto;
    }

    public void setFechaVoto(LocalDateTime fechaVoto) {
        this.fechaVoto = fechaVoto;
    }
}
