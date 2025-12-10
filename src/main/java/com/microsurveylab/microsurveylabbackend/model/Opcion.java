package com.microsurveylab.microsurveylabbackend.model;

import jakarta.persistence.*;

@Entity
public class Opcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;

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
