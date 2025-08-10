// src/main/java/com/banquito/core/cuentas/controlador/ComisionCargoControlador.java
package com.banquito.core.cuentas.controlador;

import com.banquito.core.cuentas.dto.ComisionCargoRequestDTO;
import com.banquito.core.cuentas.dto.ComisionCargoResponseDTO;
import com.banquito.core.cuentas.servicio.ComisionCargoServicio;
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
@RequestMapping("/api/configuracion-cuentas/v1/comisiones-cargos")
@Tag(name = "Comisiones-Cargos", description = "CRUD de comisiones y cargos")
@Slf4j
public class ComisionCargoControlador {
    private final ComisionCargoServicio service;

    public ComisionCargoControlador(ComisionCargoServicio service) {
        this.service = service;
    }

    @Operation(summary = "Listar comisiones/cargos activos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado devuelto", content = @Content(schema = @Schema(implementation = ComisionCargoResponseDTO.class)))
    })
    @GetMapping
    public List<ComisionCargoResponseDTO> listar() {
        log.info("GET /api/cuentas/comisiones-cargos → listar");
        return service.listarActivas();
    }

    @Operation(summary = "Obtener comisión/cargo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Recurso encontrado", content = @Content(schema = @Schema(implementation = ComisionCargoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No existe recurso con ese ID")
    })
    @GetMapping("/{id}")
    public ComisionCargoResponseDTO getById(
            @Parameter(description = "ID de la comisión/cargo", required = true) @PathVariable String id) {
        log.info("GET /api/cuentas/comisiones-cargos/{} → obtener", id);
        return service.obtenerPorId(id);
    }

    @Operation(summary = "Crear nueva comisión/cargo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Recurso creado", content = @Content(schema = @Schema(implementation = ComisionCargoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o recurso duplicado")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComisionCargoResponseDTO crear(
            @Parameter(description = "Payload de creación", required = true) @Valid @RequestBody ComisionCargoRequestDTO dto) {
        log.info("POST /api/cuentas/comisiones-cargos → crear tipo={} servicio={}",
                dto.getTipoComision(), dto.getServicioId());
        return service.crear(dto);
    }

    @Operation(summary = "Actualizar comisión/cargo existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Recurso actualizado", content = @Content(schema = @Schema(implementation = ComisionCargoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No existe recurso con ese ID"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ComisionCargoResponseDTO actualizar(
            @Parameter(description = "ID del recurso", required = true) @PathVariable String id,
            @Parameter(description = "Payload de actualización", required = true) @Valid @RequestBody ComisionCargoRequestDTO dto) {
        log.info("PUT /api/cuentas/comisiones-cargos/{} → actualizar", id);
        return service.actualizar(id, dto);
    }

    @Operation(summary = "Eliminar (lógico) comisión/cargo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Recurso marcado como inactivo"),
            @ApiResponse(responseCode = "404", description = "No existe recurso con ese ID")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(
            @Parameter(description = "ID del recurso", required = true) @PathVariable String id) {
        log.info("DELETE /api/cuentas/comisiones-cargos/{} → borrar lógicamente", id);
        service.eliminar(id);
    }
}
