package com.banquito.core.cuentas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TipoCuentaRequestDTO {
    @NotBlank
    private String idMoneda;
    @NotBlank
    private String idTasaInteresPorDefecto;
    @NotBlank
    private String nombre;
    private String descripcion;
    @NotBlank
    private String tipoCliente;
}