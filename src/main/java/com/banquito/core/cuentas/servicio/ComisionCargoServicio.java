package com.banquito.core.cuentas.servicio;

import com.banquito.core.cuentas.dto.ComisionCargoRequestDTO;
import com.banquito.core.cuentas.dto.ComisionCargoResponseDTO;
import com.banquito.core.cuentas.excepciones.RecursoDuplicadoException;
import com.banquito.core.cuentas.excepciones.RecursoNoEncontradoException;
import com.banquito.core.cuentas.mapper.ComisionCargoMapper;
import com.banquito.core.cuentas.modelo.ComisionCargo;
import com.banquito.core.cuentas.repositorio.ComisionCargoRepositorio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ComisionCargoServicio {
    private final ComisionCargoRepositorio repo;

    public ComisionCargoServicio(ComisionCargoRepositorio repo) {
        this.repo = repo;
    }

    /** Lista todas las comisiones/cargos activos */
    public List<ComisionCargoResponseDTO> listarActivas() {
        log.info("Listando comisiones/cargos activos");
        return repo.findByEstado(ComisionCargo.ESTADO_ACTIVO)
                .stream()
                .map(ComisionCargoMapper::toDto)
                .collect(Collectors.toList());
    }

    /** Obtiene una comisión/cargo por ID */
    public ComisionCargoResponseDTO obtenerPorId(String id) {
        log.info("Obteniendo comision/cargo {}", id);
        ComisionCargo e = repo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("ComisionCargo", id));
        return ComisionCargoMapper.toDto(e);
    }

    /** Crea una nueva comisión/cargo (evitando duplicados) */
    public ComisionCargoResponseDTO crear(ComisionCargoRequestDTO dto) {
        log.info("Creando comision/cargo tipo={} servicio={}", dto.getTipoComision(), dto.getServicioId());
        boolean dup = repo.existsByTipoComisionAndServicioIdAndEstado(
                dto.getTipoComision(), dto.getServicioId(), ComisionCargo.ESTADO_ACTIVO);
        if (dup) {
            throw new RecursoDuplicadoException("Ya existe una comision/cargo activa para este tipo y servicio");
        }
        ComisionCargo e = ComisionCargoMapper.toEntity(dto);
        e.setFechaCreacion(LocalDateTime.now());
        e.setFechaModificacion(LocalDateTime.now());
        ComisionCargo saved = repo.save(e);
        return ComisionCargoMapper.toDto(saved);
    }

    /** Actualiza una comisión/cargo existente */
    public ComisionCargoResponseDTO actualizar(String id, ComisionCargoRequestDTO dto) {
        log.info("Actualizando comision/cargo {}", id);
        ComisionCargo e = repo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("ComisionCargo", id));

        e.setTipoComision(dto.getTipoComision());
        e.setNombre(dto.getNombre());
        e.setBaseCalculo(dto.getBaseCalculo());
        e.setMonto(dto.getMonto());
        e.setFrecuencia(dto.getFrecuencia());
        e.setServicioId(dto.getServicioId());
        e.setDescripcion(dto.getDescripcion());
        e.setFechaModificacion(LocalDateTime.now());
        e.setVersion(e.getVersion() + 1);

        ComisionCargo updated = repo.save(e);
        return ComisionCargoMapper.toDto(updated);
    }

    /** Borrado lógico: marca la comisión/cargo como INACTIVO */
    public void eliminar(String id) {
        log.info("Desactivando comision/cargo {}", id);
        ComisionCargo e = repo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("ComisionCargo", id));
        if (!ComisionCargo.ESTADO_INACTIVO.equals(e.getEstado())) {
            e.setEstado(ComisionCargo.ESTADO_INACTIVO);
            e.setFechaModificacion(LocalDateTime.now());
            e.setVersion(e.getVersion() + 1);
            repo.save(e);
        }
    }
}