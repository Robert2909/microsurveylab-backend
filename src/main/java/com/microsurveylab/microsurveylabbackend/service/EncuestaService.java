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

/**
 * Lógica principal del módulo de encuestas.
 *
 * Este service se encarga de:
 * - Crear encuestas con sus opciones
 * - Consultar encuestas
 * - Cambiar estado (activa/inactiva)
 * - Actualizar datos básicos
 * - Eliminar encuestas (y limpiar votos relacionados)
 *
 * La idea es mantener el controller liviano y concentrar aquí las reglas.
 */
@Service
@Transactional
public class EncuestaService {

    private final EncuestaRepository encuestaRepository;
    private final VotoRepository votoRepository;

    public EncuestaService(EncuestaRepository encuestaRepository, VotoRepository votoRepository) {
        this.encuestaRepository = encuestaRepository;
        this.votoRepository = votoRepository;
    }

    // Listado general para el frontend.
    public List<EncuestaResponseDTO> obtenerTodas() {
        List<Encuesta> encuestas = encuestaRepository.findAll();

        // Se mapea a DTO para no mandar la entidad tal cual.
        return encuestas.stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    // Búsqueda por id con error claro cuando no existe.
    public EncuestaResponseDTO obtenerPorId(Long id) {
        Encuesta encuesta = encuestaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Encuesta no encontrada con id: " + id));

        return mapearAResponseDTO(encuesta);
    }

    // Crea una encuesta y genera sus opciones a partir del request.
    public EncuestaResponseDTO crear(EncuestaRequestDTO request) {
        validarOpciones(request.getOpciones());

        Encuesta encuesta = new Encuesta();
        encuesta.setPregunta(request.getPregunta());
        encuesta.setDescripcion(request.getDescripcion());
        encuesta.setActiva(true); // por defecto nace activa para poder votar de inmediato

        // Armamos las opciones y las ligamos a la encuesta.
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

    // Activa la encuesta para aceptar votos.
    public void activarEncuesta(Long id) {
        Encuesta encuesta = encuestaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Encuesta no encontrada con id: " + id));

        encuesta.setActiva(true);
        encuestaRepository.save(encuesta);
    }

    // Desactiva la encuesta para cerrar votación.
    public void desactivarEncuesta(Long id) {
        Encuesta encuesta = encuestaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Encuesta no encontrada con id: " + id));

        encuesta.setActiva(false);
        encuestaRepository.save(encuesta);
    }

    /**
     * Actualiza campos básicos de la encuesta.
     * Se permite actualización parcial: solo se cambian campos que vengan en el DTO.
     * En teoría podría quitar el @Transactional si quisiera llamar save() explícitamente,
     * pero así queda más limpio.
     * Aunque algo redundante...
     * Olvidé que significaba eso de "transaccional" jeje
     */
    @Transactional
    public EncuestaResponseDTO actualizarEncuesta(Long id, EncuestaUpdateDTO dto) {
        Encuesta encuesta = encuestaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Encuesta no encontrada"));

        // Se evita pisar valores con strings vacíos.
        if (dto.getPregunta() != null && !dto.getPregunta().isBlank()) {
            encuesta.setPregunta(dto.getPregunta().trim());
        }

        // Aquí permitimos que la descripción pueda quedar vacía si así se manda.
        if (dto.getDescripcion() != null) {
            encuesta.setDescripcion(dto.getDescripcion().trim());
        }

        // Si viene el flag, se respeta.
        if (dto.getActiva() != null) {
            encuesta.setActiva(dto.getActiva());
        }

        // No hace falta llamar save(): al estar en transacción, JPA hace el update al final.
        return mapearAResponseDTO(encuesta);
    }

    /**
     * Elimina la encuesta y también borra los votos ligados.
     * Esto evita dejar registros huérfanos o inconsistencias.
     * Aqui también podría quitar el @Transactional y llamar save() explícitamente,
     * pero tengo la costumbre de dejarlo así para operaciones de escritura.
     */
    @Transactional
    public void eliminarEncuesta(Long id) {
        Encuesta encuesta = encuestaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Encuesta no encontrada"));

        // Primero se limpian votos para no chocar con llaves foráneas.
        votoRepository.deleteByEncuesta(encuesta);

        encuestaRepository.delete(encuesta);
    }

    // Validación rápida del lado del backend para no guardar encuestas incompletas.
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

    // Mapea entidad -> DTO. Aquí decidimos exactamente qué datos se regresan al frontend.
    private EncuestaResponseDTO mapearAResponseDTO(Encuesta encuesta) {
        EncuestaResponseDTO dto = new EncuestaResponseDTO();
        dto.setId(encuesta.getId());
        dto.setPregunta(encuesta.getPregunta());
        dto.setDescripcion(encuesta.getDescripcion());
        dto.setActiva(encuesta.getActiva());
        dto.setFechaCreacion(encuesta.getFechaCreacion());

        // El frontend solo necesita id y texto por opción.
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
