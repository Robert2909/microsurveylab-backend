package com.microsurveylab.microsurveylabbackend.controller;

import com.microsurveylab.microsurveylabbackend.dto.ResultadoEncuestaDTO;
import com.microsurveylab.microsurveylabbackend.dto.VotoRequestDTO;
import com.microsurveylab.microsurveylabbackend.service.VotoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/encuestas/{encuestaId}")
@CrossOrigin(origins = "*")
public class VotoController {

    private final VotoService votoService;

    public VotoController(VotoService votoService) {
        this.votoService = votoService;
    }

    @PostMapping("/votos")
    public ResponseEntity<Void> registrarVoto(@PathVariable Long encuestaId, @Valid @RequestBody VotoRequestDTO request) {
        votoService.registrarVoto(encuestaId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/resultados")
    public ResponseEntity<ResultadoEncuestaDTO> obtenerResultados(@PathVariable Long encuestaId) {
        ResultadoEncuestaDTO dto = votoService.obtenerResultados(encuestaId);
        return ResponseEntity.ok(dto);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> manejarIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> manejarRuntime(RuntimeException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}
