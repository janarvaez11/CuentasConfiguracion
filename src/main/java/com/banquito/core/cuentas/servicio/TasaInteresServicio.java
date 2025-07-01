// src/main/java/com/banquito/core/cuentas/servicio/TasaInteresServicio.java
package com.banquito.core.cuentas.servicio;

import com.banquito.core.cuentas.dto.*;
import com.banquito.core.cuentas.excepciones.RecursoNoEncontradoException;
import com.banquito.core.cuentas.mapper.TasaInteresMapper;
import com.banquito.core.cuentas.modelo.TasaInteres;
import com.banquito.core.cuentas.repositorio.TasaInteresRepositorio;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TasaInteresServicio {

    private final TasaInteresRepositorio repo;

    public TasaInteresServicio(TasaInteresRepositorio repo) {
        this.repo = repo;
    }

    public List<TasaInteresResponseDTO> listarTodos() {
        return repo.findAll().stream()
                .map(TasaInteresMapper::toDto)
                .collect(Collectors.toList());
    }

    public TasaInteresResponseDTO obtenerPorId(String id) {
        TasaInteres e = repo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("TasaInteres", id));
        return TasaInteresMapper.toDto(e);
    }

    public TasaInteresResponseDTO crear(TasaInteresRequestDTO dto) {
        TasaInteres e = TasaInteresMapper.toEntity(dto);
        e.setFechaInicioVigencia(dto.getFechaInicioVigencia());
        // si quieres, e.setFechaModificacion(null) o igual a inicio
        try {
            TasaInteres guardada = repo.save(e);
            return TasaInteresMapper.toDto(guardada);
        } catch (DuplicateKeyException ex) {
            throw new DuplicateKeyException(
                    "Ya existe una tasa con la misma combinación base/metodo/frecuencia");
        }
    }

    public TasaInteresResponseDTO actualizar(String id, TasaInteresRequestDTO dto) {
        TasaInteres e = repo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("TasaInteres", id));

        e.setBaseCalculo(dto.getBaseCalculo());
        e.setMetodoCalculo(dto.getMetodoCalculo());
        e.setFrecuenciaCapitalizacion(dto.getFrecuenciaCapitalizacion());
        e.setFechaFinVigencia(dto.getFechaFinVigencia());
        e.setFechaModificacion(LocalDateTime.now());
        e.setVersion(e.getVersion() + 1);

        // actualizar listas:
        e.setPlazos(TasaInteresMapper.toEntity(dto).getPlazos());
        e.setSaldos(TasaInteresMapper.toEntity(dto).getSaldos());

        TasaInteres updated = repo.save(e);
        return TasaInteresMapper.toDto(updated);
    }

    /** Borrado lógico: marca como INACTIVO */
    public void eliminar(String id) {
        TasaInteres e = repo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("TasaInteres", id));
        e.setEstado(TasaInteres.ESTADO_INACTIVO);
        e.setFechaModificacion(LocalDateTime.now());
        e.setVersion(e.getVersion() + 1);
        repo.save(e);
    }
}
