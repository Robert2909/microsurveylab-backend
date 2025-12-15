package com.microsurveylab.microsurveylabbackend.repository;

import com.microsurveylab.microsurveylabbackend.model.Opcion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Acceso a datos para Opcion.
 *
 * Se usa principalmente para buscar una opción por id al momento de votar.
 */
public interface OpcionRepository extends JpaRepository<Opcion, Long> {
}
