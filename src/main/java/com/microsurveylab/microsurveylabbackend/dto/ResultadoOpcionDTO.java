package com.microsurveylab.microsurveylabbackend.dto;

public class ResultadoOpcionDTO {

    private Long opcionId;
    private String texto;
    private long totalVotos;
    private double porcentaje;

    public Long getOpcionId() {
        return opcionId;
    }

    public void setOpcionId(Long opcionId) {
        this.opcionId = opcionId;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public long getTotalVotos() {
        return totalVotos;
    }

    public void setTotalVotos(long totalVotos) {
        this.totalVotos = totalVotos;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }
}
