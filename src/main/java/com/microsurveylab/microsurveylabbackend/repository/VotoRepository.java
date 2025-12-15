package com.microsurveylab.microsurveylabbackend.repository;

import com.microsurveylab.microsurveylabbackend.model.Encuesta;
import com.microsurveylab.microsurveylabbackend.model.Opcion;
import com.microsurveylab.microsurveylabbackend.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Acceso a datos para Voto.
 *
 * Aquí sí agregamos métodos extra porque:
 * - necesitamos contar votos para armar resultados
 * - necesitamos borrar votos ligados a una encuesta cuando se elimina
 *
 * Spring Data genera estas consultas a partir del nombre del método.
 */
public interface VotoRepository extends JpaRepository<Voto, Long> {

    // Cuenta votos totales de una encuesta.
    long countByEncuesta(Encuesta encuesta);

    // Cuenta votos de una opción dentro de una encuesta.
    long countByEncuestaAndOpcion(Encuesta encuesta, Opcion opcion);

    // Limpieza de votos cuando se elimina una encuesta.
    void deleteByEncuesta(Encuesta encuesta);
}
