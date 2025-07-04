package com.banquito.core.cuentas.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class ServicioAsociadoRequestDTO {
    @NotBlank
    private String nombre;
    private String descripcion;
}