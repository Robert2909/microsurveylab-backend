package com.microsurveylab.microsurveylabbackend.controller;

import com.microsurveylab.microsurveylabbackend.dto.ResultadoEncuestaDTO;
import com.microsurveylab.microsurveylabbackend.dto.VotoRequestDTO;
import com.microsurveylab.microsurveylabbackend.service.VotoService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoints relacionados con votación y resultados.
 *
 * Se cuelga de la ruta /api/encuestas/{encuestaId} para dejar claro
 * que todo aquí depende de una encuesta específica.
 */
@RestController
@RequestMapping("/api/encuestas/{encuestaId}")
@CrossOrigin(origins = "*") // Facilita la comunicación con el frontend mientras desarrollo.
public class VotoController {

    private final VotoService votoService;

    public VotoController(VotoService votoService) {
        this.votoService = votoService;
    }

    // Registra un voto en una encuesta.
    // Si la encuesta está cerrada o el request es inválido, el service puede lanzar excepción.
    @PostMapping("/votos")
    public ResponseEntity<Void> registrarVoto(@PathVariable Long encuestaId, @Valid @RequestBody VotoRequestDTO request) {
        votoService.registrarVoto(encuestaId, request);
        return ResponseEntity.ok().build();
    }

    // Obtiene el conteo/resultado actual de una encuesta.
    @GetMapping("/resultados")
    public ResponseEntity<ResultadoEncuestaDTO> obtenerResultados(@PathVariable Long encuestaId) {
        ResultadoEncuestaDTO dto = votoService.obtenerResultados(encuestaId);
        return ResponseEntity.ok(dto);
    }

    // Errores esperados por reglas de negocio (como votar en encuesta cerrada o algo asi).
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> manejarIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    // Esto captura "lo demás". Idealmente se manejaría con excepciones más específicas.
    // Se deja por ahora para devolver un 404 con mensaje cuando algo no se encuentra.
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> manejarNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}
