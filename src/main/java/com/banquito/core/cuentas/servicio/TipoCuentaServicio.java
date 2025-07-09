// src/main/java/com/banquito/core/cuentas/servicio/TipoCuentaServicio.java
package com.banquito.core.cuentas.servicio;

import com.banquito.core.cuentas.dto.TipoCuentaRequestDTO;
import com.banquito.core.cuentas.dto.TipoCuentaResponseDTO;
import com.banquito.core.cuentas.excepciones.RecursoNoEncontradoException;
import com.banquito.core.cuentas.excepciones.OperacionNoPermitidaException;
import com.banquito.core.cuentas.mapper.TipoCuentaMapper;
import com.banquito.core.cuentas.modelo.TipoCuenta;
import com.banquito.core.cuentas.repositorio.TipoCuentaRepositorio;
import com.banquito.core.cuentas.repositorio.TasaInteresRepositorio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TipoCuentaServicio {

    private final TipoCuentaRepositorio repo;
    private final TasaInteresRepositorio tasaRepo;

    public TipoCuentaServicio(TipoCuentaRepositorio repo,
                              TasaInteresRepositorio tasaRepo) {
        this.repo = repo;
        this.tasaRepo = tasaRepo;
    }

    @Transactional(readOnly = true)
    public List<TipoCuentaResponseDTO> listarTodosActivos() {
        log.debug("Listando todos los tipos de cuenta ACTIVOS");
        return repo.findByEstado(TipoCuenta.ESTADO_ACTIVO).stream()
                   .map(TipoCuentaMapper::toDto)
                   .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TipoCuentaResponseDTO obtenerPorId(String id) {
        log.debug("Obteniendo TipoCuenta id={}", id);
        TipoCuenta ent = repo.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("TipoCuenta", id));
        return TipoCuentaMapper.toDto(ent);
    }

    @Transactional
    public TipoCuentaResponseDTO crear(TipoCuentaRequestDTO dto) {
        log.info("Creando TipoCuenta nombre={}", dto.getNombre());
        validarTasaInteres(dto.getIdTasaInteresPorDefecto());

        TipoCuenta ent = TipoCuentaMapper.toEntity(dto);
        ent.setFechaCreacion(LocalDateTime.now());
        ent.setEstado(TipoCuenta.ESTADO_ACTIVO);
        ent.setVersion(1);

        TipoCuenta guardada = repo.save(ent);
        log.info("TipoCuenta creado id={}", guardada.getId());
        return TipoCuentaMapper.toDto(guardada);
    }

    @Transactional
    public TipoCuentaResponseDTO actualizar(String id, TipoCuentaRequestDTO dto) {
        log.info("Actualizando TipoCuenta id={}", id);
        TipoCuenta ent = repo.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("TipoCuenta", id));

        validarTasaInteres(dto.getIdTasaInteresPorDefecto());

        ent.setIdMoneda(dto.getIdMoneda());
        ent.setIdTasaInteresPorDefecto(dto.getIdTasaInteresPorDefecto());
        ent.setNombre(dto.getNombre());
        ent.setDescripcion(dto.getDescripcion());
        ent.setTipoCliente(dto.getTipoCliente());
        ent.setFechaModificacion(LocalDateTime.now());
        ent.setVersion(ent.getVersion() + 1);

        TipoCuenta actualizada = repo.save(ent);
        log.info("TipoCuenta actualizado id={}", id);
        return TipoCuentaMapper.toDto(actualizada);
    }

    @Transactional
    public void eliminarLogico(String id) {
        log.info("Eliminación lógica de TipoCuenta id={}", id);
        TipoCuenta ent = repo.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("TipoCuenta", id));

        if (TipoCuenta.ESTADO_INACTIVO.equals(ent.getEstado())) {
            throw new OperacionNoPermitidaException("TipoCuenta ya está inactivo");
        }

        ent.setEstado(TipoCuenta.ESTADO_INACTIVO);
        ent.setFechaModificacion(LocalDateTime.now());
        ent.setVersion(ent.getVersion() + 1);
        repo.save(ent);

        log.info("TipoCuenta id={} marcado como INACTIVO", id);
    }

    /** Si no existe la Tasa en Mongo, lanza OperacionNoPermitidaException */
    private void validarTasaInteres(String idTasa) {
        log.debug("Validando existencia de TasaInteres id={}", idTasa);
        if (!tasaRepo.existsById(idTasa)) {
            throw new OperacionNoPermitidaException(
                "TasaInteres no existe: " + idTasa
            );
        }
    }
}
