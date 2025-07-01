package com.banquito.core.cuentas.servicio;

import com.banquito.core.cuentas.dto.TipoCuentaRequestDTO;
import com.banquito.core.cuentas.dto.TipoCuentaResponseDTO;
import com.banquito.core.cuentas.excepciones.RecursoNoEncontradoException;
import com.banquito.core.cuentas.mapper.TipoCuentaMapper;
import com.banquito.core.cuentas.modelo.TipoCuenta;
import com.banquito.core.cuentas.repositorio.TipoCuentaRepositorio;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoCuentaServicio {

  private final TipoCuentaRepositorio repositorio;

  public TipoCuentaServicio(TipoCuentaRepositorio repositorio) {
    this.repositorio = repositorio;
  }

  /** Lista todas las Tipos de Cuenta como DTOs */
  public List<TipoCuentaResponseDTO> listarTodos() {
    return repositorio.findAll().stream()
        .map(TipoCuentaMapper::toDto)
        .collect(Collectors.toList());
  }

  /** Obtiene un TipoCuenta por ID o lanza 404 */
  public TipoCuentaResponseDTO obtenerPorId(String id) {
    TipoCuenta entidad = repositorio.findById(id)
        .orElseThrow(() -> new RecursoNoEncontradoException("TipoCuenta", id));
    return TipoCuentaMapper.toDto(entidad);
  }

  /** Crea un nuevo TipoCuenta y devuelve su DTO */
  public TipoCuentaResponseDTO crear(TipoCuentaRequestDTO dto) {
    TipoCuenta entidad = TipoCuentaMapper.toEntity(dto);
    entidad.setFechaCreacion(LocalDateTime.now());
    entidad.setEstado(TipoCuenta.ESTADO_ACTIVO);
    entidad.setVersion(1);
    TipoCuenta guardada = repositorio.save(entidad);
    return TipoCuentaMapper.toDto(guardada);
  }

  /** Actualiza un TipoCuenta existente o lanza 404 */
  public TipoCuentaResponseDTO actualizar(String id, TipoCuentaRequestDTO dto) {
    TipoCuenta actual = repositorio.findById(id)
        .orElseThrow(() -> new RecursoNoEncontradoException("TipoCuenta", id));
    // Aplica cambios
    actual.setIdMoneda(dto.getIdMoneda());
    actual.setIdTasaInteresPorDefecto(dto.getIdTasaInteresPorDefecto());
    actual.setNombre(dto.getNombre());
    actual.setDescripcion(dto.getDescripcion());
    actual.setTipoCliente(dto.getTipoCliente());
    actual.setFechaModificacion(LocalDateTime.now());
    actual.setVersion(actual.getVersion() + 1);
    TipoCuenta guardada = repositorio.save(actual);
    return TipoCuentaMapper.toDto(guardada);
  }

  public void eliminar(String id) {
    TipoCuenta entidad = repositorio.findById(id)
        .orElseThrow(() -> new RecursoNoEncontradoException("TipoCuenta", id));
    entidad.setEstado(TipoCuenta.ESTADO_INACTIVO);
    entidad.setFechaModificacion(LocalDateTime.now());
    entidad.setVersion(entidad.getVersion() + 1);
    repositorio.save(entidad);
  }
}
