package com.banquito.core.cuentas.controlador;

import com.banquito.core.cuentas.modelo.TipoCuenta;
import com.banquito.core.cuentas.servicio.TipoCuentaServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-cuentas")
public class TipoCuentaControlador {

    private final TipoCuentaServicio servicio;

    public TipoCuentaControlador(TipoCuentaServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<TipoCuenta> listar() {
        return servicio.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoCuenta> obtenerPorId(@PathVariable String id) {
        return servicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TipoCuenta crear(@RequestBody TipoCuenta tipoCuenta) {
        return servicio.crear(tipoCuenta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoCuenta> actualizar(
            @PathVariable String id,
            @RequestBody TipoCuenta cambios) {
        try {
            return ResponseEntity.ok(servicio.actualizar(id, cambios));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
