package com.banquito.core.cuentas.modelo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class TasaSaldo {
    private BigDecimal saldoMinimo;
    private BigDecimal saldoMaximo;
    private BigDecimal tasa;
    private String estado;
    private Integer version;
}
