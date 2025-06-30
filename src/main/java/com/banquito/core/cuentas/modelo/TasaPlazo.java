package com.banquito.core.cuentas.modelo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class TasaPlazo {
    private Integer minMeses;
    private Integer maxMeses;
    private BigDecimal tasa;
    private String estado;
    private Long version;
}
