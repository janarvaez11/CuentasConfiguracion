package com.banquito.core.cuentas.servicio;

import com.banquito.core.cuentas.modelo.TipoCuenta;
import com.banquito.core.cuentas.repositorio.TipoCuentaRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoCuentaServicio {

  private final TipoCuentaRepositorio repositorio;

  public TipoCuentaServicio(TipoCuentaRepositorio repositorio) {
    this.repositorio = repositorio;
  }

  public List<TipoCuenta> listarTodos() {
    return repositorio.findAll();
  }

  public Optional<TipoCuenta> obtenerPorId(String id) {
    return repositorio.findById(id);
  }

  public TipoCuenta crear(TipoCuenta tipoCuenta) {
    tipoCuenta.setFechaCreacion(java.time.LocalDateTime.now());
    tipoCuenta.setVersion(1);
    return repositorio.save(tipoCuenta);
  }

  public TipoCuenta actualizar(String id, TipoCuenta cambios) {
    return repositorio.findById(id)
      .map(existing -> {
        existing.setNombre(cambios.getNombre());
        existing.setDescripcion(cambios.getDescripcion());
        existing.setEstado(cambios.getEstado());
        existing.setFechaModificacion(java.time.LocalDateTime.now());
        existing.setVersion(existing.getVersion() + 1);
        return repositorio.save(existing);
      })
      .orElseThrow(() -> new RuntimeException("TipoCuenta no existe"));
  }

  public void eliminar(String id) {
    repositorio.deleteById(id);
  }
}
