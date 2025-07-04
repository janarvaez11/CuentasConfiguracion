// ServicioAsociadoMapper.java
package com.banquito.core.cuentas.mapper;
import com.banquito.core.cuentas.dto.ServicioAsociadoRequestDTO;
import com.banquito.core.cuentas.dto.ServicioAsociadoResponseDTO;
import com.banquito.core.cuentas.modelo.ServicioAsociado;

public class ServicioAsociadoMapper {
  public static ServicioAsociado toEntity(ServicioAsociadoRequestDTO dto) {
    ServicioAsociado e = new ServicioAsociado();
    e.setNombre(dto.getNombre());
    e.setDescripcion(dto.getDescripcion());
    return e;
  }

  public static ServicioAsociadoResponseDTO toDto(ServicioAsociado e) {
    return new ServicioAsociadoResponseDTO(
      e.getId(),
      e.getNombre(),
      e.getDescripcion(),
      e.getEstado(),
      e.getFechaCreacion(),
      e.getFechaModificacion(),
      e.getVersion()
    );
  }
}