package com.banquito.core.cuentas.controlador;

import com.banquito.core.cuentas.dto.ServicioAsociadoRequestDTO;
import com.banquito.core.cuentas.dto.ServicioAsociadoResponseDTO;
import com.banquito.core.cuentas.servicio.ServicioAsociadoServicio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/servicios-asociados")
@Slf4j
public class ServicioAsociadoControlador {

    private final ServicioAsociadoServicio service;

    public ServicioAsociadoControlador(ServicioAsociadoServicio service) {
        this.service = service;
    }

    /** GET  / → listar activos */
    @GetMapping
    public List<ServicioAsociadoResponseDTO> listar() {
        log.info("GET /api/servicios-asociados → listar activos");
        return service.listarActivos();
    }

    /** GET /{id} → obtener por ID */
    @GetMapping("/{id}")
    public ServicioAsociadoResponseDTO getById(@PathVariable String id) {
        log.info("GET /api/servicios-asociados/{} → obtener por ID", id);
        return service.obtenerPorId(id);
    }

    /** POST / → crear */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServicioAsociadoResponseDTO crear(
            @Valid @RequestBody ServicioAsociadoRequestDTO dto) {
        log.info("POST /api/servicios-asociados → crear");
        return service.crear(dto);
    }

    /** PUT /{id} → actualizar */
    @PutMapping("/{id}")
    public ServicioAsociadoResponseDTO actualizar(
            @PathVariable String id,
            @Valid @RequestBody ServicioAsociadoRequestDTO dto) {
        log.info("PUT /api/servicios-asociados/{} → actualizar", id);
        return service.actualizar(id, dto);
    }

    /** DELETE /{id} → borrado lógico */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String id) {
        log.info("DELETE /api/servicios-asociados/{} → borrar lógicamente", id);
        service.eliminar(id);
    }
}
