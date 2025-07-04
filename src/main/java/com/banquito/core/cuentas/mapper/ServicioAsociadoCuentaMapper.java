package com.banquito.core.cuentas.mapper;

import com.banquito.core.cuentas.dto.ServicioAsociadoCuentaRequestDTO;
import com.banquito.core.cuentas.dto.ServicioAsociadoCuentaResponseDTO;
import com.banquito.core.cuentas.modelo.ServicioAsociadoCuenta;

import java.time.LocalDateTime;

public class ServicioAsociadoCuentaMapper {

    /** Crea la entidad a partir del DTO de petición */
    public static ServicioAsociadoCuenta toEntity(ServicioAsociadoCuentaRequestDTO dto) {
        if (dto == null) return null;

        ServicioAsociadoCuenta e = new ServicioAsociadoCuenta();
        e.setServicioId(dto.getServicioId());
        e.setCuentaClienteId(dto.getCuentaClienteId());
        e.setFechaAsignacion(LocalDateTime.now());
        e.setEstado(ServicioAsociadoCuenta.ESTADO_ACTIVO);
        e.setVersion(1L);
        return e;
    }

    /** Crea el DTO de respuesta a partir de la entidad */
    public static ServicioAsociadoCuentaResponseDTO toDto(ServicioAsociadoCuenta e) {
        if (e == null) return null;

        return new ServicioAsociadoCuentaResponseDTO(
            e.getId(),
            e.getServicioId(),
            e.getCuentaClienteId(),
            e.getFechaAsignacion(),
            e.getEstado(),
            e.getVersion()
        );
    }
}
