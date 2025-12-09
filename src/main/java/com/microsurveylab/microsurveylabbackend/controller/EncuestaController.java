package com.microsurveylab.microsurveylabbackend.controller;

import com.microsurveylab.microsurveylabbackend.dto.EncuestaRequestDTO;
import com.microsurveylab.microsurveylabbackend.dto.EncuestaResponseDTO;
import com.microsurveylab.microsurveylabbackend.service.EncuestaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/encuestas")
@CrossOrigin(origins = "*")
public class EncuestaController {

    private final EncuestaService encuestaService;

    public EncuestaController(EncuestaService encuestaService) {
        this.encuestaService = encuestaService;
    }

    // GET /api/encuestas
    @GetMapping
    public List<EncuestaResponseDTO> obtenerTodas() {
        return encuestaService.obtenerTodas();
    }

    // GET /api/encuestas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EncuestaResponseDTO> obtenerPorId(@PathVariable Long id) {
        EncuestaResponseDTO dto = encuestaService.obtenerPorId(id);
        return ResponseEntity.ok(dto);
    }

    // POST /api/encuestas
    @PostMapping
    public ResponseEntity<EncuestaResponseDTO> crear(@Valid @RequestBody EncuestaRequestDTO request) {
        EncuestaResponseDTO creada = encuestaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    // PUT /api/encuestas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<EncuestaResponseDTO> actualizar(@PathVariable Long id,
                                                          @Valid @RequestBody EncuestaRequestDTO request) {
        EncuestaResponseDTO actualizada = encuestaService.actualizar(id, request);
        return ResponseEntity.ok(actualizada);
    }

    // DELETE /api/encuestas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        encuestaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/activar")
    public ResponseEntity<Void> activar(@PathVariable Long id) {
        encuestaService.activarEncuesta(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        encuestaService.desactivarEncuesta(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> manejarIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> manejarRuntime(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
