package com.banquito.core.cuentas.controlador;

import com.banquito.core.cuentas.dto.ComisionCargoCuentaRequestDTO;
import com.banquito.core.cuentas.dto.ComisionCargoCuentaResponseDTO;
import com.banquito.core.cuentas.dto.ComisionCargoCuentaDetalleDTO;
import com.banquito.core.cuentas.dto.TransaccionesRespuestaDTO;
import com.banquito.core.cuentas.servicio.ComisionCargoCuentaServicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comisiones-cargos-cuentas")
@Slf4j
public class ComisionCargoCuentaController {

    private final ComisionCargoCuentaServicio service;

    public ComisionCargoCuentaController(ComisionCargoCuentaServicio service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComisionCargoCuentaResponseDTO asignar(
            @Valid @RequestBody ComisionCargoCuentaRequestDTO dto) {
        log.info("POST /api/comisiones-cargos-cuentas → asignar");
        return service.asignar(dto);
    }

    @PutMapping("/{id}/desactivar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desactivar(@PathVariable String id) {
        log.info("PUT /api/comisiones-cargos-cuentas/{}/desactivar", id);
        service.desactivar(id);
    }

    @GetMapping("/por-cuenta/{cuentaClienteId}")
    public List<ComisionCargoCuentaDetalleDTO> listarPorCuenta(
            @PathVariable String cuentaClienteId) {
        log.info("GET /api/comisiones-cargos-cuentas/por-cuenta/{}", cuentaClienteId);
        return service.listarPorCuenta(cuentaClienteId);
    }

    @PostMapping("/{id}/cobrar")
    public TransaccionesRespuestaDTO cobrar(@PathVariable String id) {
        log.info("POST /api/comisiones-cargos-cuentas/{}/cobrar", id);
        return service.procesarCobro(id);
    }
}