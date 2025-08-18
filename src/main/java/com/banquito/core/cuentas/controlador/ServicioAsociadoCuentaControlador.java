// src/main/java/com/banquito/core/cuentas/controlador/ServicioAsociadoCuentaControlador.java
package com.banquito.core.cuentas.controlador;

import com.banquito.core.cuentas.dto.ServicioAsociadoCuentaDetalleDTO;
import com.banquito.core.cuentas.dto.ServicioAsociadoCuentaRequestDTO;
import com.banquito.core.cuentas.dto.ServicioAsociadoCuentaResponseDTO;
import com.banquito.core.cuentas.servicio.ServicioAsociadoCuentaServicio;
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
@RequestMapping("/v1/servicios-asociados-cuentas")
@Tag(name = "Servicios-Asociados-Cuentas", description = "Asignación y gestión de servicios asociados a cuentas")
@Slf4j
public class ServicioAsociadoCuentaControlador {

    private final ServicioAsociadoCuentaServicio service;

    public ServicioAsociadoCuentaControlador(ServicioAsociadoCuentaServicio service) {
        this.service = service;
    }

    @Operation(summary = "Listar detalle de servicios por cuenta", description = "Devuelve todos los servicios asociados y activos para una cuenta cliente dada")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalle devuelto", content = @Content(schema = @Schema(implementation = ServicioAsociadoCuentaDetalleDTO.class)))
    })
    @GetMapping("/por-cuenta/{cuentaClienteId}")
    public List<ServicioAsociadoCuentaDetalleDTO> listarDetallePorCuenta(
            @Parameter(description = "ID de la cuenta cliente", required = true) @PathVariable String cuentaClienteId) {
        log.info("GET /api/cuentas/servicios-asociados-cuentas/por-cuenta/{} → listar detalle", cuentaClienteId);
        return service.listarServiciosPorCuenta(cuentaClienteId);
    }

    @Operation(summary = "Asignar servicio a cuenta", description = "Crea una nueva asignación de un servicio asociado a una cuenta cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Asignación creada", content = @Content(schema = @Schema(implementation = ServicioAsociadoCuentaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o asignación duplicada")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServicioAsociadoCuentaResponseDTO asignar(
            @Parameter(description = "Payload con datos de la asignación", required = true) @Valid @RequestBody ServicioAsociadoCuentaRequestDTO dto) {
        log.info("POST /api/cuentas/servicios-asociados-cuentas → asignar servicioId={} a cuentaClienteId={}",
                dto.getServicioId(), dto.getCuentaClienteId());
        return service.asignarServicio(dto);
    }

    @Operation(summary = "Desactivar asignación", description = "Marca como inactiva la asignación de un servicio a la cuenta cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Asignación desactivada"),
            @ApiResponse(responseCode = "404", description = "No se encontró la asignación")
    })
    @PutMapping("/{id}/desactivar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desactivar(
            @Parameter(description = "ID de la asignación", required = true) @PathVariable String id) {
        log.info("PUT /api/cuentas/servicios-asociados-cuentas/{}/desactivar", id);
        service.desactivarAsignacion(id);
    }
}
