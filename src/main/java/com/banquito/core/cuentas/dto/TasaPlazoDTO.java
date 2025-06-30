// src/main/java/com/banquito/core/cuentas/dto/TasaPlazoDTO.java
package com.banquito.core.cuentas.dto;

import jakarta.validation.constraints.Min;
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
public class TasaPlazoDTO {
    @NotNull
    @Min(0)
    private Integer minMeses;

    @NotNull
    @Min(0)
    private Integer maxMeses;

    @NotNull
    private BigDecimal tasa;

    private String estado;
    private Long version;
}
