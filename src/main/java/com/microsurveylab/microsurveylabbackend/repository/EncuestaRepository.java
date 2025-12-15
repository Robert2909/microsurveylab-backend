package com.microsurveylab.microsurveylabbackend.repository;

import com.microsurveylab.microsurveylabbackend.model.Encuesta;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Acceso a datos para Encuesta.
 *
 * Con JpaRepository ya tenemos lo básico: findAll, findById, save, delete.
 * Por ahora no se requieren consultas personalizadas aquí.
 */
public interface EncuestaRepository extends JpaRepository<Encuesta, Long> {
}
