// src/main/java/com/banquito/core/cuentas/controlador/v1/TipoCuentaControlador.java
package com.banquito.core.cuentas.controlador;

import com.banquito.core.cuentas.dto.TipoCuentaRequestDTO;
import com.banquito.core.cuentas.dto.TipoCuentaResponseDTO;
import com.banquito.core.cuentas.servicio.TipoCuentaServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configuracion-cuentas/v1/tipos-cuentas")
@Tag(name = "v1 – Tipos-Cuentas", description = "CRUD sobre los tipos de cuenta")
@Slf4j
public class TipoCuentaControlador {

    private final TipoCuentaServicio servicio;

    public TipoCuentaControlador(TipoCuentaServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    @Operation(summary = "Listar Tipos de Cuenta activos")
    public List<TipoCuentaResponseDTO> listar() {
        log.info("GET /api/cuentas/v1/tipos-cuentas");
        return servicio.listarTodosActivos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener TipoCuenta por ID")
    public TipoCuentaResponseDTO obtener(@PathVariable String id) {
        log.info("GET /api/cuentas/v1/tipos-cuentas/{}", id);
        return servicio.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear nuevo TipoCuenta")
    public TipoCuentaResponseDTO crear(
        @Valid @RequestBody TipoCuentaRequestDTO dto) {
        log.info("POST /api/v1/tipos-cuentas nombre={}", dto.getNombre());
        return servicio.crear(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar TipoCuenta existente")
    public TipoCuentaResponseDTO actualizar(
        @PathVariable String id,
        @Valid @RequestBody TipoCuentaRequestDTO dto) {
        log.info("PUT /api/cuentas/v1/tipos-cuentas/{}", id);
        return servicio.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminación lógica de TipoCuenta")
    public void eliminar(@PathVariable String id) {
        log.info("DELETE /api/cuentas/v1/tipos-cuentas/{}", id);
        servicio.eliminarLogico(id);
    }
}
