package com.banquito.core.cuentas.mapper;

import com.banquito.core.cuentas.dto.ComisionCargoCuentaRequestDTO;
import com.banquito.core.cuentas.dto.ComisionCargoCuentaResponseDTO;
import com.banquito.core.cuentas.modelo.ComisionCargoCuenta;

import java.time.LocalDateTime;

public class ComisionCargoCuentaMapper {

    public static ComisionCargoCuenta toEntity(ComisionCargoCuentaRequestDTO dto) {
        ComisionCargoCuenta e = new ComisionCargoCuenta();
        e.setComisionCargoId(dto.getComisionCargoId());
        e.setCuentaClienteId(dto.getCuentaClienteId());
        e.setFechaAsignacion(LocalDateTime.now());
        e.setEstado(ComisionCargoCuenta.ESTADO_ACTIVO);
        e.setVersion(1L);
        return e;
    }

    public static ComisionCargoCuentaResponseDTO toDto(ComisionCargoCuenta e) {
        return new ComisionCargoCuentaResponseDTO(
                e.getId(),
                e.getComisionCargoId(),
                e.getCuentaClienteId(),
                e.getFechaAsignacion(),
                e.getEstado(),
                e.getVersion());
    }
}