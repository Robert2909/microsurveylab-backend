package com.microsurveylab.microsurveylabbackend.service;

import com.microsurveylab.microsurveylabbackend.dto.EncuestaRequestDTO;
import com.microsurveylab.microsurveylabbackend.dto.EncuestaResponseDTO;
import com.microsurveylab.microsurveylabbackend.dto.EncuestaUpdateDTO;
import com.microsurveylab.microsurveylabbackend.model.Encuesta;
import com.microsurveylab.microsurveylabbackend.model.Opcion;
import com.microsurveylab.microsurveylabbackend.repository.EncuestaRepository;
import com.microsurveylab.microsurveylabbackend.repository.VotoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EncuestaService {

    private final EncuestaRepository encuestaRepository;
    private final VotoRepository votoRepository;

    public EncuestaService(EncuestaRepository encuestaRepository,
                           VotoRepository votoRepository) {
        this.encuestaRepository = encuestaRepository;
        this.votoRepository = votoRepository;
    }

    public List<EncuestaResponseDTO> obtenerTodas() {
        List<Encuesta> encuestas = encuestaRepository.findAll();
        return encuestas.stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    public EncuestaResponseDTO obtenerPorId(Long id) {
        Encuesta encuesta = encuestaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Encuesta no encontrada con id: " + id));
        return mapearAResponseDTO(encuesta);
    }

    public EncuestaResponseDTO crear(EncuestaRequestDTO request) {
        validarOpciones(request.getOpciones());

        Encuesta encuesta = new Encuesta();
        encuesta.setPregunta(request.getPregunta());
        encuesta.setDescripcion(request.getDescripcion());
        encuesta.setActiva(true);

        List<Opcion> opciones = new ArrayList<>();
        for (String textoOpcion : request.getOpciones()) {
            Opcion opcion = new Opcion();
            opcion.setTexto(textoOpcion);
            opcion.setEncuesta(encuesta);
            opciones.add(opcion);
        }
        encuesta.setOpciones(opciones);

        Encuesta guardada = encuestaRepository.save(encuesta);
        return mapearAResponseDTO(guardada);
    }

    public void activarEncuesta(Long id) {
        Encuesta encuesta = encuestaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Encuesta no encontrada con id: " + id));
        encuesta.setActiva(true);
        encuestaRepository.save(encuesta);
    }

    public void desactivarEncuesta(Long id) {
        Encuesta encuesta = encuestaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Encuesta no encontrada con id: " + id));
        encuesta.setActiva(false);
        encuestaRepository.save(encuesta);
    }

    @Transactional
    public EncuestaResponseDTO actualizarEncuesta(Long id, EncuestaUpdateDTO dto) {
        Encuesta encuesta = encuestaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Encuesta no encontrada"));

        if (dto.getPregunta() != null && !dto.getPregunta().isBlank()) {
            encuesta.setPregunta(dto.getPregunta().trim());
        }

        if (dto.getDescripcion() != null) {
            encuesta.setDescripcion(dto.getDescripcion().trim());
        }

        if (dto.getActiva() != null) {
            encuesta.setActiva(dto.getActiva());
        }

        return mapearAResponseDTO(encuesta);
    }

    @Transactional
    public void eliminarEncuesta(Long id) {
        Encuesta encuesta = encuestaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Encuesta no encontrada"));

        votoRepository.deleteByEncuesta(encuesta);

        encuestaRepository.delete(encuesta);
    }

    private void validarOpciones(List<String> opciones) {
        if (opciones == null || opciones.size() < 2) {
            throw new IllegalArgumentException("La encuesta debe tener al menos 2 opciones");
        }

        boolean tieneVacios = opciones.stream()
                .anyMatch(op -> op == null || op.trim().isEmpty());

        if (tieneVacios) {
            throw new IllegalArgumentException("Todas las opciones deben tener texto");
        }
    }

    private EncuestaResponseDTO mapearAResponseDTO(Encuesta encuesta) {
        EncuestaResponseDTO dto = new EncuestaResponseDTO();
        dto.setId(encuesta.getId());
        dto.setPregunta(encuesta.getPregunta());
        dto.setDescripcion(encuesta.getDescripcion());
        dto.setActiva(encuesta.getActiva());
        dto.setFechaCreacion(encuesta.getFechaCreacion());

        List<EncuestaResponseDTO.OpcionRespuestaDTO> opcionesDTO =
                encuesta.getOpciones().stream().map(opcion -> {
                    EncuestaResponseDTO.OpcionRespuestaDTO o = new EncuestaResponseDTO.OpcionRespuestaDTO();
                    o.setId(opcion.getId());
                    o.setTexto(opcion.getTexto());
                    return o;
                }).collect(Collectors.toList());

        dto.setOpciones(opcionesDTO);

        return dto;
    }
}
