package com.banquito.core.cuentas.controlador;

import com.banquito.core.cuentas.dto.ServicioAsociadoCuentaDetalleDTO;
import com.banquito.core.cuentas.dto.ServicioAsociadoCuentaRequestDTO;
import com.banquito.core.cuentas.dto.ServicioAsociadoCuentaResponseDTO;
import com.banquito.core.cuentas.servicio.ServicioAsociadoCuentaServicio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/servicios-asociados-cuentas")
@Slf4j
public class ServicioAsociadoCuentaControlador {

    private final ServicioAsociadoCuentaServicio service;

    public ServicioAsociadoCuentaControlador(ServicioAsociadoCuentaServicio service) {
        this.service = service;
    }

    @GetMapping("/por-cuenta/{cuentaClienteId}")
    public List<ServicioAsociadoCuentaDetalleDTO> listarDetallePorCuenta(
            @PathVariable String cuentaClienteId) {
        log.info("GET /por-cuenta/{cuentaClienteId} → listar detalle");
        return service.listarServiciosPorCuenta(cuentaClienteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServicioAsociadoCuentaResponseDTO asignar(
            @Valid @RequestBody ServicioAsociadoCuentaRequestDTO dto) {
        log.info("POST /api/servicios-asociados-cuentas -> asignar servicio a cuenta");
        return service.asignarServicio(dto);
    }

    @PutMapping("/{id}/desactivar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desactivar(@PathVariable String id) {
        log.info("PUT /api/servicios-asociados-cuentas/{}/desactivar", id);
        service.desactivarAsignacion(id);
    }

}
