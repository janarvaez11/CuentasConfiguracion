// src/main/java/com/banquito/core/cuentas/controlador/TipoCuentaControlador.java
package com.banquito.core.cuentas.controlador;

import com.banquito.core.cuentas.dto.TipoCuentaRequestDTO;
import com.banquito.core.cuentas.dto.TipoCuentaResponseDTO;
import com.banquito.core.cuentas.servicio.TipoCuentaServicio;
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
@RequestMapping("/api/tipos-cuentas")
@Tag(name = "Tipos-Cuentas", description = "CRUD sobre los tipos de cuenta")
@Slf4j
public class TipoCuentaControlador {

    private final TipoCuentaServicio servicio;

    public TipoCuentaControlador(TipoCuentaServicio servicio) {
        this.servicio = servicio;
    }

    @Operation(summary = "Listar todos los tipos de cuenta", description = "Devuelve un listado de todos los tipos de cuenta disponibles")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado devuelto", content = @Content(schema = @Schema(implementation = TipoCuentaResponseDTO.class)))
    })
    @GetMapping
    public List<TipoCuentaResponseDTO> listar() {
        log.info("GET /api/tipos-cuentas → listar todos");
        return servicio.listarTodos();
    }

    @Operation(summary = "Obtener tipo de cuenta por ID", description = "Devuelve los datos de un tipo de cuenta específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de cuenta encontrado", content = @Content(schema = @Schema(implementation = TipoCuentaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tipo de cuenta no encontrado")
    })
    @GetMapping("/{id}")
    public TipoCuentaResponseDTO obtenerPorId(
            @Parameter(description = "ID del tipo de cuenta", required = true) @PathVariable String id) {
        log.info("GET /api/tipos-cuentas/{} → obtener por ID", id);
        return servicio.obtenerPorId(id);
    }

    @Operation(summary = "Crear nuevo tipo de cuenta", description = "Registra un nuevo tipo de cuenta en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tipo de cuenta creado", content = @Content(schema = @Schema(implementation = TipoCuentaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TipoCuentaResponseDTO crear(
            @Parameter(description = "Payload con datos del nuevo tipo de cuenta", required = true) @Valid @RequestBody TipoCuentaRequestDTO request) {
        log.info("POST /api/tipos-cuentas → crear nombre={}", request.getNombre());
        return servicio.crear(request);
    }

    @Operation(summary = "Actualizar tipo de cuenta existente", description = "Modifica los datos de un tipo de cuenta dado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de cuenta actualizado", content = @Content(schema = @Schema(implementation = TipoCuentaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tipo de cuenta no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public TipoCuentaResponseDTO actualizar(
            @Parameter(description = "ID del tipo de cuenta", required = true) @PathVariable String id,
            @Parameter(description = "Payload con datos a actualizar", required = true) @Valid @RequestBody TipoCuentaRequestDTO request) {
        log.info("PUT /api/tipos-cuentas/{} → actualizar", id);
        return servicio.actualizar(id, request);
    }

    @Operation(summary = "Eliminar (lógico) tipo de cuenta", description = "Marca un tipo de cuenta como inactivo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tipo de cuenta eliminado"),
            @ApiResponse(responseCode = "404", description = "Tipo de cuenta no encontrado")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(
            @Parameter(description = "ID del tipo de cuenta", required = true) @PathVariable String id) {
        log.info("DELETE /api/tipos-cuentas/{} → eliminar lógicamente", id);
        servicio.eliminar(id);
    }
}
