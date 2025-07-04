package com.banquito.core.cuentas.mapper;

import com.banquito.core.cuentas.dto.ComisionCargoRequestDTO;
import com.banquito.core.cuentas.dto.ComisionCargoResponseDTO;
import com.banquito.core.cuentas.modelo.ComisionCargo;

import java.time.LocalDateTime;

public class ComisionCargoMapper {
    public static ComisionCargo toEntity(ComisionCargoRequestDTO dto) {
        ComisionCargo e = new ComisionCargo();
        e.setTipoComision(dto.getTipoComision());
        e.setNombre(dto.getNombre());
        e.setBaseCalculo(dto.getBaseCalculo());
        e.setMonto(dto.getMonto());
        e.setFrecuencia(dto.getFrecuencia());
        e.setServicioId(dto.getServicioId());
        e.setDescripcion(dto.getDescripcion());
        e.setEstado(ComisionCargo.ESTADO_ACTIVO);
        e.setFechaCreacion(LocalDateTime.now());
        e.setFechaModificacion(LocalDateTime.now());
        e.setVersion(1L);
        return e;
    }

    public static ComisionCargoResponseDTO toDto(ComisionCargo e) {
        return new ComisionCargoResponseDTO(
                e.getId(),
                e.getTipoComision(),
                e.getNombre(),
                e.getBaseCalculo(),
                e.getMonto(),
                e.getFrecuencia(),
                e.getServicioId(),
                e.getDescripcion(),
                e.getEstado(),
                e.getFechaCreacion(),
                e.getFechaModificacion(),
                e.getVersion());
    }
}