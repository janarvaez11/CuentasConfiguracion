package com.banquito.core.cuentas.mapper;
import com.banquito.core.cuentas.dto.TipoCuentaRequestDTO;
import com.banquito.core.cuentas.dto.TipoCuentaResponseDTO;
import com.banquito.core.cuentas.modelo.TipoCuenta;

public class TipoCuentaMapper {
    public static TipoCuenta toEntity(TipoCuentaRequestDTO dto) {
        TipoCuenta t = new TipoCuenta();
        t.setIdMoneda(dto.getIdMoneda());
        t.setIdTasaInteresPorDefecto(dto.getIdTasaInteresPorDefecto());
        t.setNombre(dto.getNombre());
        t.setDescripcion(dto.getDescripcion());
        t.setTipoCliente(dto.getTipoCliente());
        return t;
    }

    public static TipoCuentaResponseDTO toDto(TipoCuenta entity) {
        TipoCuentaResponseDTO r = new TipoCuentaResponseDTO();
        r.setId(entity.getId());
        r.setNombre(entity.getNombre());
        r.setDescripcion(entity.getDescripcion());
        r.setTipoCliente(entity.getTipoCliente());
        r.setEstado(entity.getEstado());
        r.setVersion(entity.getVersion());
        return r;
    }
}
