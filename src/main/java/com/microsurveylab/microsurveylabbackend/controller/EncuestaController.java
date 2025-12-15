package com.microsurveylab.microsurveylabbackend.controller;

import com.microsurveylab.microsurveylabbackend.dto.EncuestaRequestDTO;
import com.microsurveylab.microsurveylabbackend.dto.EncuestaResponseDTO;
import com.microsurveylab.microsurveylabbackend.dto.EncuestaUpdateDTO;
import com.microsurveylab.microsurveylabbackend.service.EncuestaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para administrar encuestas (CRUD).
 *
 * Idea general:
 * - El controller recibe la petición HTTP.
 * - Valida lo básico (por ejemplo @Valid en el request).
 * - Delega la lógica real al service.
 *
 * Nota: Se usa DTO para no exponer directamente las entidades.
 */
@RestController
@RequestMapping("/api/encuestas")
@CrossOrigin(origins = "*") // En desarrollo evitamos problemas de CORS con el frontend, luego no se usa.
public class EncuestaController {

    private final EncuestaService encuestaService;

    public EncuestaController(EncuestaService encuestaService) {
        this.encuestaService = encuestaService;
    }

    // Devuelve la lista completa de encuestas (para el listado del frontend).
    @GetMapping
    public List<EncuestaResponseDTO> obtenerTodas() {
        return encuestaService.obtenerTodas();
    }

    // Obtiene una encuesta específica. Si no existe, regresa 404.
    @GetMapping("/{id}")
    public ResponseEntity<EncuestaResponseDTO> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(encuestaService.obtenerPorId(id));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // Crea una encuesta nueva. Si todo sale bien regresa 201 (Created).
    @PostMapping
    public ResponseEntity<EncuestaResponseDTO> crear(@Valid @RequestBody EncuestaRequestDTO request) {
        EncuestaResponseDTO creada = encuestaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    // Actualiza una encuesta existente. Si el id no existe, se devuelve 404.
    @PutMapping("/{id}")
    public ResponseEntity<EncuestaResponseDTO> actualizarEncuesta(
            @PathVariable Long id,
            @Valid @RequestBody EncuestaUpdateDTO dto
    ) {
        try {
            return ResponseEntity.ok(encuestaService.actualizarEncuesta(id, dto));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // Elimina una encuesta por id. Si no existe, 404.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEncuesta(@PathVariable Long id) {
        try {
            encuestaService.eliminarEncuesta(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // Activa una encuesta (osea, que pueda recibir votos).
    @PostMapping("/{id}/activar")
    public ResponseEntity<Void> activar(@PathVariable Long id) {
        encuestaService.activarEncuesta(id);
        return ResponseEntity.ok().build();
    }

    // Desactiva una encuesta (pues para cerrar votación).
    @PostMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        encuestaService.desactivarEncuesta(id);
        return ResponseEntity.ok().build();
    }

    // Captura errores de validación de negocio (ej. datos inconsistentes).
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> manejarIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
