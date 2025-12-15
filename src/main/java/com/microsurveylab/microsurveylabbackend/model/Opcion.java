package com.microsurveylab.microsurveylabbackend.model;

import jakarta.persistence.*;

/**
 * Entidad que representa una opción dentro de una encuesta.
 *
 * Cada opción pertenece a una sola encuesta.
 */
@Entity
public class Opcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;

    /**
     * Muchas opciones pueden pertenecer a una encuesta.
     *
     * fetch LAZY para evitar cargar la encuesta completa
     * cuando solo se necesita la opción.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encuesta_id")
    private Encuesta encuesta;

    public Opcion() { }

    public Long getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Encuesta getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(Encuesta encuesta) {
        this.encuesta = encuesta;
    }
}
