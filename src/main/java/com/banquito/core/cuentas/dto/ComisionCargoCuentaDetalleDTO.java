package com.banquito.core.cuentas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ComisionCargoCuentaDetalleDTO {
    private String id;
    private String nombre;
    private BigDecimal monto;
    private String frecuencia;
    private LocalDateTime fechaAsignacion;
    private String estado;
    private Long version;
}