package com.banquito.core.cuentas.servicio;

import com.banquito.core.cuentas.dto.ServicioAsociadoRequestDTO;
import com.banquito.core.cuentas.dto.ServicioAsociadoResponseDTO;
import com.banquito.core.cuentas.excepciones.RecursoNoEncontradoException;
import com.banquito.core.cuentas.mapper.ServicioAsociadoMapper;
import com.banquito.core.cuentas.modelo.ServicioAsociado;
import com.banquito.core.cuentas.repositorio.ServicioAsociadoRepositorio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ServicioAsociadoServicio {

    private final ServicioAsociadoRepositorio repo;

    public ServicioAsociadoServicio(ServicioAsociadoRepositorio repo) {
        this.repo = repo;
    }

    /** Lista todos los servicios con estado ACTIVO */
    public List<ServicioAsociadoResponseDTO> listarActivos() {
        log.info("Listando servicios activos");
        return repo.findByEstado(ServicioAsociado.ESTADO_ACTIVO)
                   .stream()
                   .map(ServicioAsociadoMapper::toDto)
                   .collect(Collectors.toList());
    }

    /** Busca un servicio por ID, sin importar su estado */
    public ServicioAsociadoResponseDTO obtenerPorId(String id) {
        log.info("Obteniendo servicio con ID {}", id);
        ServicioAsociado e = repo.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("ServicioAsociado", id));
        return ServicioAsociadoMapper.toDto(e);
    }

    /** Crea un nuevo servicio (inicialmente ACTIVO) */
    public ServicioAsociadoResponseDTO crear(ServicioAsociadoRequestDTO dto) {
        log.info("Creando servicio {}", dto.getNombre());
        ServicioAsociado e = ServicioAsociadoMapper.toEntity(dto);
        e.setFechaCreacion(LocalDateTime.now());
        e.setFechaModificacion(LocalDateTime.now());
        e.setEstado(ServicioAsociado.ESTADO_ACTIVO);
        e.setVersion(1L);
        ServicioAsociado guardado = repo.save(e);
        return ServicioAsociadoMapper.toDto(guardado);
    }

    /** Actualiza campos básicos y version/fecha */
    public ServicioAsociadoResponseDTO actualizar(String id, ServicioAsociadoRequestDTO dto) {
        log.info("Actualizando servicio {}", id);
        ServicioAsociado e = repo.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("ServicioAsociado", id));

        e.setNombre(dto.getNombre());
        e.setDescripcion(dto.getDescripcion());
        e.setFechaModificacion(LocalDateTime.now());
        e.setVersion(e.getVersion() + 1);

        ServicioAsociado updated = repo.save(e);
        return ServicioAsociadoMapper.toDto(updated);
    }

    /** Borrado lógico: marca como INACTIVO */
    public void eliminar(String id) {
        log.info("Desactivando servicio {}", id);
        ServicioAsociado e = repo.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("ServicioAsociado", id));

        if (!ServicioAsociado.ESTADO_INACTIVO.equals(e.getEstado())) {
            e.setEstado(ServicioAsociado.ESTADO_INACTIVO);
            e.setFechaModificacion(LocalDateTime.now());
            e.setVersion(e.getVersion() + 1);
            repo.save(e);
        }
    }
}
