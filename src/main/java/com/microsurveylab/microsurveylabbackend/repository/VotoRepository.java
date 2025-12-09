package com.microsurveylab.microsurveylabbackend.repository;

import com.microsurveylab.microsurveylabbackend.model.Encuesta;
import com.microsurveylab.microsurveylabbackend.model.Opcion;
import com.microsurveylab.microsurveylabbackend.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<Voto, Long> {

    long countByEncuesta(Encuesta encuesta);

    long countByEncuestaAndOpcion(Encuesta encuesta, Opcion opcion);
}
