// src/main/java/com/banquito/core/cuentas/controlador/ServicioAsociadoControlador.java
package com.banquito.core.cuentas.controlador;

import com.banquito.core.cuentas.dto.ServicioAsociadoRequestDTO;
import com.banquito.core.cuentas.dto.ServicioAsociadoResponseDTO;
import com.banquito.core.cuentas.servicio.ServicioAsociadoServicio;
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
@RequestMapping("/api/transferencias/v1/servicios-asociados")
@Tag(name = "Servicios-Asociados", description = "Gestión de servicios asociados")
@Slf4j
public class ServicioAsociadoControlador {

    private final ServicioAsociadoServicio service;

    public ServicioAsociadoControlador(ServicioAsociadoServicio service) {
        this.service = service;
    }

    @Operation(summary = "Listar servicios asociados activos", description = "Devuelve todos los servicios con estado ACTIVO")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido", content = @Content(schema = @Schema(implementation = ServicioAsociadoResponseDTO.class)))
    })
    @GetMapping
    public List<ServicioAsociadoResponseDTO> listar() {
        log.info("GET /api/cuentas/servicios-asociados → listar activos");
        return service.listarActivos();
    }

    @Operation(summary = "Obtener servicio asociado por ID", description = "Busca un servicio, sin importar su estado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Servicio encontrado", content = @Content(schema = @Schema(implementation = ServicioAsociadoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No existe servicio con ese ID")
    })
    @GetMapping("/{id}")
    public ServicioAsociadoResponseDTO getById(
            @Parameter(description = "ID del servicio", required = true) @PathVariable String id) {
        log.info("GET /api/cuentas/servicios-asociados/{} → obtener por ID", id);
        return service.obtenerPorId(id);
    }

    @Operation(summary = "Crear nuevo servicio asociado", description = "Registra un nuevo servicio, se marca como ACTIVO")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Servicio creado", content = @Content(schema = @Schema(implementation = ServicioAsociadoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServicioAsociadoResponseDTO crear(
            @Parameter(description = "Payload de creación", required = true) @Valid @RequestBody ServicioAsociadoRequestDTO dto) {
        log.info("POST /api/cuentas/servicios-asociados → crear nombre={}", dto.getNombre());
        return service.crear(dto);
    }

    @Operation(summary = "Actualizar servicio asociado", description = "Modifica nombre y descripción de un servicio existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Servicio actualizado", content = @Content(schema = @Schema(implementation = ServicioAsociadoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No existe servicio con ese ID"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ServicioAsociadoResponseDTO actualizar(
            @Parameter(description = "ID del servicio", required = true) @PathVariable String id,
            @Parameter(description = "Payload de actualización", required = true) @Valid @RequestBody ServicioAsociadoRequestDTO dto) {
        log.info("PUT /api/cuentas/servicios-asociados/{} → actualizar", id);
        return service.actualizar(id, dto);
    }

    @Operation(summary = "Eliminar (lógico) servicio asociado", description = "Marca el servicio como INACTIVO")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Servicio desactivado"),
            @ApiResponse(responseCode = "404", description = "No existe servicio con ese ID")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(
            @Parameter(description = "ID del servicio", required = true) @PathVariable String id) {
        log.info("DELETE /api/cuentas/servicios-asociados/{} → borrar lógicamente", id);
        service.eliminar(id);
    }
}
