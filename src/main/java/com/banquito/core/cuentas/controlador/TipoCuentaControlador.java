package com.banquito.core.cuentas.controlador;

import com.banquito.core.cuentas.dto.TipoCuentaRequestDTO;
import com.banquito.core.cuentas.dto.TipoCuentaResponseDTO;
import com.banquito.core.cuentas.servicio.TipoCuentaServicio;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-cuentas")
public class TipoCuentaControlador {

    private final TipoCuentaServicio servicio;

    public TipoCuentaControlador(TipoCuentaServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<TipoCuentaResponseDTO> listar() {
        return servicio.listarTodos();
    }

    @GetMapping("/{id}")
    public TipoCuentaResponseDTO obtenerPorId(@PathVariable String id) {
        return servicio.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TipoCuentaResponseDTO crear(
            @Valid @RequestBody TipoCuentaRequestDTO request
    ) {
        return servicio.crear(request);
    }

    @PutMapping("/{id}")
    public TipoCuentaResponseDTO actualizar(
            @PathVariable String id,
            @Valid @RequestBody TipoCuentaRequestDTO request
    ) {
        return servicio.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String id) {
        servicio.eliminar(id);
    }
}
