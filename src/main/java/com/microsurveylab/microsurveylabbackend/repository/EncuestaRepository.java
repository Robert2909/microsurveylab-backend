package com.microsurveylab.microsurveylabbackend.repository;

import com.microsurveylab.microsurveylabbackend.model.Encuesta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EncuestaRepository extends JpaRepository<Encuesta, Long> {
}
