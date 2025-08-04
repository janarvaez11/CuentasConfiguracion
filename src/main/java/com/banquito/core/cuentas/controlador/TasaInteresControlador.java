// src/main/java/com/banquito/core/cuentas/controlador/TasaInteresControlador.java
package com.banquito.core.cuentas.controlador;

import com.banquito.core.cuentas.dto.TasaInteresRequestDTO;
import com.banquito.core.cuentas.dto.TasaInteresResponseDTO;
import com.banquito.core.cuentas.servicio.TasaInteresServicio;
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
@RequestMapping("/api/cuentas/v1/tasas-intereses")
@Tag(name = "Tasas-Intereses", description = "Operaciones CRUD sobre tasas de interés")
@Slf4j
public class TasaInteresControlador {

    private final TasaInteresServicio servicio;

    public TasaInteresControlador(TasaInteresServicio servicio) {
        this.servicio = servicio;
    }

    @Operation(summary = "Listar todas las tasas de interés", description = "Devuelve un listado de todas las tasas de interés registradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de tasas devuelto", content = @Content(schema = @Schema(implementation = TasaInteresResponseDTO.class)))
    })
    @GetMapping
    public List<TasaInteresResponseDTO> listar() {
        log.info("GET /api/cuentas/v1/tasas-intereses → listar todas");
        return servicio.listarTodos();
    }

    @Operation(summary = "Obtener tasa de interés por ID", description = "Devuelve los datos de una tasa de interés existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasa encontrada", content = @Content(schema = @Schema(implementation = TasaInteresResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tasa no encontrada")
    })
    @GetMapping("/{id}")
    public TasaInteresResponseDTO obtener(
            @Parameter(description = "ID de la tasa de interés", required = true) @PathVariable String id) {
        log.info("GET /api/cuentas/v1/tasas-intereses/{} → obtener por ID", id);
        return servicio.obtenerPorId(id);
    }

    @Operation(summary = "Crear nueva tasa de interés", description = "Registra una nueva tasa de interés en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tasa creada", content = @Content(schema = @Schema(implementation = TasaInteresResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de solicitud inválidos")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TasaInteresResponseDTO crear(
            @Parameter(description = "Payload con datos de la nueva tasa", required = true) @Valid @RequestBody TasaInteresRequestDTO dto) {
        log.info("POST /api/cuentas/v1/tasas-intereses → crear tasa con nombre={}");
        return servicio.crear(dto);
    }

    @Operation(summary = "Actualizar tasa de interés existente", description = "Modifica los datos de una tasa de interés existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasa actualizada", content = @Content(schema = @Schema(implementation = TasaInteresResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tasa no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos de solicitud inválidos")
    })
    @PutMapping("/{id}")
    public TasaInteresResponseDTO actualizar(
            @Parameter(description = "ID de la tasa a actualizar", required = true) @PathVariable String id,
            @Parameter(description = "Payload con datos actualizados", required = true) @Valid @RequestBody TasaInteresRequestDTO dto) {
        log.info("PUT /api/cuentas/v1/tasas-intereses/{} → actualizar", id);
        return servicio.actualizar(id, dto);
    }

    @Operation(summary = "Eliminar (lógico) tasa de interés", description = "Marca una tasa de interés como inactiva")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tasa eliminada"),
            @ApiResponse(responseCode = "404", description = "Tasa no encontrada")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(
            @Parameter(description = "ID de la tasa a eliminar", required = true) @PathVariable String id) {
        log.info("DELETE /api/cuentas/v1/tasas-intereses/{} → eliminar lógicamente", id);
        servicio.eliminar(id);
    }
}
