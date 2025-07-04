package com.banquito.core.cuentas.servicio;

import com.banquito.core.cuentas.dto.ServicioAsociadoCuentaDetalleDTO;
import com.banquito.core.cuentas.dto.ServicioAsociadoCuentaRequestDTO;
import com.banquito.core.cuentas.dto.ServicioAsociadoCuentaResponseDTO;
import com.banquito.core.cuentas.excepciones.RecursoDuplicadoException;
import com.banquito.core.cuentas.excepciones.RecursoNoEncontradoException;
import com.banquito.core.cuentas.mapper.ServicioAsociadoCuentaMapper;
import com.banquito.core.cuentas.modelo.ServicioAsociado;
import com.banquito.core.cuentas.modelo.ServicioAsociadoCuenta;
import com.banquito.core.cuentas.repositorio.ServicioAsociadoCuentaRepositorio;
import com.banquito.core.cuentas.repositorio.ServicioAsociadoRepositorio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ServicioAsociadoCuentaServicio {

    private final ServicioAsociadoCuentaRepositorio repo;
    private final ServicioAsociadoRepositorio servicioRepo;

    public ServicioAsociadoCuentaServicio(ServicioAsociadoCuentaRepositorio repo,
            ServicioAsociadoRepositorio servicioRepo) {
        this.repo = repo;
        this.servicioRepo = servicioRepo;
    }

    public List<ServicioAsociadoCuentaDetalleDTO> listarServiciosPorCuenta(String cuentaClienteId) {
        log.info("Listando servicios (detalle) para cuentaCliente={}", cuentaClienteId);

        return repo
                .findByCuentaClienteIdAndEstado(cuentaClienteId, ServicioAsociadoCuenta.ESTADO_ACTIVO)
                .stream()
                .map(asign -> {
                    ServicioAsociado serv = servicioRepo.findById(asign.getServicioId())
                            .orElseThrow(() -> new RecursoNoEncontradoException(
                                    "ServicioAsociado", asign.getServicioId()));

                    return new ServicioAsociadoCuentaDetalleDTO(
                            asign.getId(),
                            serv.getNombre(),
                            serv.getDescripcion(),
                            asign.getFechaAsignacion(),
                            asign.getEstado(),
                            asign.getVersion());
                })
                .collect(Collectors.toList());
    }

    public ServicioAsociadoCuentaResponseDTO asignarServicio(ServicioAsociadoCuentaRequestDTO dto) {
        log.info("Asignando servicio '{}' a cuentaCliente '{}'", dto.getServicioId(), dto.getCuentaClienteId());

        boolean existe = repo.existsByServicioIdAndCuentaClienteIdAndEstado(
                dto.getServicioId(),
                dto.getCuentaClienteId(),
                ServicioAsociadoCuenta.ESTADO_ACTIVO);
        if (existe) {
            log.warn("La asignación ya existe y está activa");
            throw new RecursoDuplicadoException("Ya existe esta asignación de servicio para la cuenta");
        }

        ServicioAsociadoCuenta entidad = ServicioAsociadoCuentaMapper.toEntity(dto);
        ServicioAsociadoCuenta guardada = repo.save(entidad);
        return ServicioAsociadoCuentaMapper.toDto(guardada);
    }

    public void desactivarAsignacion(String id) {
        log.info("Desactivando asignación con ID '{}'", id);
        ServicioAsociadoCuenta entidad = repo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("ServicioAsociadoCuenta", id));

        if (!ServicioAsociadoCuenta.ESTADO_INACTIVO.equals(entidad.getEstado())) {
            entidad.setEstado(ServicioAsociadoCuenta.ESTADO_INACTIVO);
            entidad.setVersion(entidad.getVersion() + 1);
            repo.save(entidad);
            log.info("Asignación desactivada");
        } else {
            log.info("Asignación ya estaba inactiva");
        }
    }


    public List<ServicioAsociadoCuentaResponseDTO> listarPorCuenta(String cuentaClienteId) {
        log.info("Listando asignaciones activas para cuentaCliente '{}'", cuentaClienteId);
        return repo.findByCuentaClienteIdAndEstado(cuentaClienteId, ServicioAsociadoCuenta.ESTADO_ACTIVO)
                .stream()
                .map(ServicioAsociadoCuentaMapper::toDto)
                .collect(Collectors.toList());
    }
}
