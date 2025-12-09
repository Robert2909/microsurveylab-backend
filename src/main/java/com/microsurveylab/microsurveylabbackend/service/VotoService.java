package com.microsurveylab.microsurveylabbackend.service;

import com.microsurveylab.microsurveylabbackend.dto.ResultadoEncuestaDTO;
import com.microsurveylab.microsurveylabbackend.dto.ResultadoOpcionDTO;
import com.microsurveylab.microsurveylabbackend.dto.VotoRequestDTO;
import com.microsurveylab.microsurveylabbackend.model.Encuesta;
import com.microsurveylab.microsurveylabbackend.model.Opcion;
import com.microsurveylab.microsurveylabbackend.model.Voto;
import com.microsurveylab.microsurveylabbackend.repository.EncuestaRepository;
import com.microsurveylab.microsurveylabbackend.repository.OpcionRepository;
import com.microsurveylab.microsurveylabbackend.repository.VotoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class VotoService {

    private final EncuestaRepository encuestaRepository;
    private final OpcionRepository opcionRepository;
    private final VotoRepository votoRepository;

    public VotoService(EncuestaRepository encuestaRepository,
                       OpcionRepository opcionRepository,
                       VotoRepository votoRepository) {
        this.encuestaRepository = encuestaRepository;
        this.opcionRepository = opcionRepository;
        this.votoRepository = votoRepository;
    }

    // ==========================
    //       REGISTRAR VOTO
    // ==========================

    public void registrarVoto(Long encuestaId, VotoRequestDTO request) {
        Encuesta encuesta = encuestaRepository.findById(encuestaId)
                .orElseThrow(() -> new RuntimeException("Encuesta no encontrada con id: " + encuestaId));

        if (Boolean.FALSE.equals(encuesta.getActiva())) {
            throw new IllegalArgumentException("La encuesta está inactiva y no acepta más votos");
        }

        Long opcionId = request.getOpcionId();
        Opcion opcion = opcionRepository.findById(opcionId)
                .orElseThrow(() -> new RuntimeException("Opción no encontrada con id: " + opcionId));

        // Validar que la opción pertenece a esa encuesta
        if (!opcion.getEncuesta().getId().equals(encuesta.getId())) {
            throw new IllegalArgumentException("La opción no pertenece a la encuesta especificada");
        }

        Voto voto = new Voto();
        voto.setEncuesta(encuesta);
        voto.setOpcion(opcion);

        votoRepository.save(voto);
    }

    // ==========================
    //      RESULTADOS ENCUESTA
    // ==========================

    public ResultadoEncuestaDTO obtenerResultados(Long encuestaId) {
        Encuesta encuesta = encuestaRepository.findById(encuestaId)
                .orElseThrow(() -> new RuntimeException("Encuesta no encontrada con id: " + encuestaId));

        long totalVotos = votoRepository.countByEncuesta(encuesta);

        List<ResultadoOpcionDTO> resultados = new ArrayList<>();

        encuesta.getOpciones().forEach(opcion -> {
            long votosOpcion = votoRepository.countByEncuestaAndOpcion(encuesta, opcion);

            ResultadoOpcionDTO r = new ResultadoOpcionDTO();
            r.setOpcionId(opcion.getId());
            r.setTexto(opcion.getTexto());
            r.setTotalVotos(votosOpcion);

            double porcentaje = 0.0;
            if (totalVotos > 0) {
                porcentaje = (votosOpcion * 100.0) / totalVotos;
            }
            r.setPorcentaje(porcentaje);

            resultados.add(r);
        });

        ResultadoEncuestaDTO dto = new ResultadoEncuestaDTO();
        dto.setEncuestaId(encuesta.getId());
        dto.setPregunta(encuesta.getPregunta());
        dto.setResultados(resultados);

        return dto;
    }
}
