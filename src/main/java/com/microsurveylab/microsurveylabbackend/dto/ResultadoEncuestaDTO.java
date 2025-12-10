package com.microsurveylab.microsurveylabbackend.dto;

import java.util.List;

public class ResultadoEncuestaDTO {

    private Long encuestaId;
    private String pregunta;
    private List<ResultadoOpcionDTO> resultados;

    public Long getEncuestaId() {
        return encuestaId;
    }

    public void setEncuestaId(Long encuestaId) {
        this.encuestaId = encuestaId;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public List<ResultadoOpcionDTO> getResultados() {
        return resultados;
    }

    public void setResultados(List<ResultadoOpcionDTO> resultados) {
        this.resultados = resultados;
    }
}
