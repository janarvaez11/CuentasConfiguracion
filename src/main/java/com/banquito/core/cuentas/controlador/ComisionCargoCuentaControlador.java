// src/main/java/com/banquito/core/cuentas/controlador/ComisionCargoCuentaControlador.java
package com.banquito.core.cuentas.controlador;

import com.banquito.core.cuentas.dto.ComisionCargoCuentaDetalleDTO;
import com.banquito.core.cuentas.dto.ComisionCargoCuentaRequestDTO;
import com.banquito.core.cuentas.dto.ComisionCargoCuentaResponseDTO;
import com.banquito.core.cuentas.dto.TransaccionesRespuestaDTO;
import com.banquito.core.cuentas.servicio.ComisionCargoCuentaServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas/comisiones-cargos-cuentas")
@Tag(name = "Comisiones-Cargos-Cuentas", description = "Asignación y cobro de comisiones/cargos a cuentas")
@Slf4j
public class ComisionCargoCuentaControlador {

    private final ComisionCargoCuentaServicio service;

    public ComisionCargoCuentaControlador(ComisionCargoCuentaServicio service) {
        this.service = service;
    }

    @Operation(summary = "Asignar comisión/cargo a cuenta", description = "Crea una nueva asignación de comisión o cargo a una cuenta cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Asignación creada", content = @Content(schema = @Schema(implementation = ComisionCargoCuentaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o duplicado")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComisionCargoCuentaResponseDTO asignar(
            @Parameter(description = "Payload de asignación", required = true) @Valid @RequestBody ComisionCargoCuentaRequestDTO dto) {
        log.info("POST /api/cuentas/comisiones-cargos-cuentas → asignar comisionCargoId={} a cuentaClienteId={}",
                dto.getComisionCargoId(), dto.getCuentaClienteId());
        return service.asignar(dto);
    }

    @Operation(summary = "Desactivar asignación", description = "Marca una asignación de comisión/cargo como inactiva (borrado lógico)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Asignación desactivada"),
            @ApiResponse(responseCode = "404", description = "No se encontró la asignación")
    })
    @PutMapping("/{id}/desactivar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desactivar(
            @Parameter(description = "ID de la asignación", required = true) @PathVariable String id) {
        log.info("PUT /api/cuentas/comisiones-cargos-cuentas/{}/desactivar", id);
        service.desactivar(id);
    }

    @Operation(summary = "Listar comisiones/cargos por cuenta", description = "Devuelve el detalle de todas las comisiones/cargos activas para una cuenta cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado devuelto", content = @Content(schema = @Schema(implementation = ComisionCargoCuentaDetalleDTO.class)))
    })
    @GetMapping("/por-cuenta/{cuentaClienteId}")
    public List<ComisionCargoCuentaDetalleDTO> listarPorCuenta(
            @Parameter(description = "ID de la cuenta cliente", required = true) @PathVariable String cuentaClienteId) {
        log.info("GET /api/cuentas/comisiones-cargos-cuentas/por-cuenta/{} → listar", cuentaClienteId);
        return service.listarPorCuenta(cuentaClienteId);
    }

    @Operation(summary = "Procesar cobro de comisión/cargo", description = "Genera una transacción de cobro de la comisión/cargo asignada")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cobro procesado", content = @Content(schema = @Schema(implementation = TransaccionesRespuestaDTO.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró la asignación")
    })
    @PostMapping("/{id}/cobrar")
    public TransaccionesRespuestaDTO cobrar(
            @Parameter(description = "ID de la asignación", required = true) @PathVariable String id) {
        log.info("POST /api/cuentas/comisiones-cargos-cuentas/{}/cobrar", id);
        return service.procesarCobro(id);
    }
}
