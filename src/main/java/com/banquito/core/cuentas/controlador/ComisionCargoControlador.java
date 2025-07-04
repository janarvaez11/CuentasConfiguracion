package com.banquito.core.cuentas.controlador;

import com.banquito.core.cuentas.dto.ComisionCargoRequestDTO;
import com.banquito.core.cuentas.dto.ComisionCargoResponseDTO;
import com.banquito.core.cuentas.servicio.ComisionCargoServicio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comisiones-cargos")
@Slf4j
public class ComisionCargoControlador {
    private final ComisionCargoServicio service;

public ComisionCargoControlador(ComisionCargoServicio service) {
    this.service = service;
}

@GetMapping
public List<ComisionCargoResponseDTO> listar() {
    log.info("GET /api/comisiones-cargos → listar");
    return service.listarActivas();
}

@GetMapping("/{id}")
public ComisionCargoResponseDTO getById(@PathVariable String id) {
    log.info("GET /api/comisiones-cargos/{} → obtener", id);
    return service.obtenerPorId(id);
}

@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public ComisionCargoResponseDTO crear(@Valid @RequestBody ComisionCargoRequestDTO dto) {
    log.info("POST /api/comisiones-cargos → crear");
    return service.crear(dto);
}

@PutMapping("/{id}")
public ComisionCargoResponseDTO actualizar(
        @PathVariable String id,
        @Valid @RequestBody ComisionCargoRequestDTO dto) {
    log.info("PUT /api/comisiones-cargos/{} → actualizar", id);
    return service.actualizar(id, dto);
}

@DeleteMapping("/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void eliminar(@PathVariable String id) {
    log.info("DELETE /api/comisiones-cargos/{} → borrar logicamente", id);
    service.eliminar(id);
}
}