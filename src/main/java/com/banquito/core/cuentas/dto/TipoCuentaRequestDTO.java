// src/main/java/com/banquito/core/cuentas/dto/TipoCuentaRequestDTO.java
package com.banquito.core.cuentas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TipoCuentaRequestDTO {
    @NotBlank(message = "La moneda es obligatoria")
    private String idMoneda;

    @NotBlank(message = "La tasa de interés por defecto es obligatoria")
    private String idTasaInteresPorDefecto;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;

    @NotBlank(message = "El tipo de cliente es obligatorio")
    @Pattern(regexp = "PERSONA|EMPRESA|AMBOS", message = "El tipoCliente debe ser PERSONA, EMPRESA o AMBOS")
    private String tipoCliente;
}
