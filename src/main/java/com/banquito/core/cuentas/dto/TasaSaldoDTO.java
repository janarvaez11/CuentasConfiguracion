// src/main/java/com/banquito/core/cuentas/dto/TasaSaldoDTO.java
package com.banquito.core.cuentas.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TasaSaldoDTO {
    @NotNull
    private BigDecimal saldoMinimo;

    @NotNull
    private BigDecimal saldoMaximo;

    @NotNull
    private BigDecimal tasa;

    private String estado;
    private Integer version;
}
