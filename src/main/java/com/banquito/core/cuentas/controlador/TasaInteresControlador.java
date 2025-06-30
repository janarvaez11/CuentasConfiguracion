// src/main/java/com/banquito/core/cuentas/controlador/TasaInteresControlador.java
package com.banquito.core.cuentas.controlador;

import com.banquito.core.cuentas.dto.TasaInteresRequestDTO;
import com.banquito.core.cuentas.dto.TasaInteresResponseDTO;
import com.banquito.core.cuentas.servicio.TasaInteresServicio;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasas-intereses")
public class TasaInteresControlador {

    private final TasaInteresServicio servicio;

    public TasaInteresControlador(TasaInteresServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<TasaInteresResponseDTO> listar() {
        return servicio.listarTodos();
    }

    @GetMapping("/{id}")
    public TasaInteresResponseDTO obtener(@PathVariable String id) {
        return servicio.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TasaInteresResponseDTO crear(@Valid @RequestBody TasaInteresRequestDTO dto) {
        return servicio.crear(dto);
    }

    @PutMapping("/{id}")
    public TasaInteresResponseDTO actualizar(
            @PathVariable String id,
            @Valid @RequestBody TasaInteresRequestDTO dto) {
        return servicio.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String id) {
        servicio.eliminar(id);
    }
}
