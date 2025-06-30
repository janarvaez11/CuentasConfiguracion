// src/main/java/com/banquito/core/cuentas/dto/TasaInteresRequestDTO.java
package com.banquito.core.cuentas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TasaInteresRequestDTO {

    @NotBlank
    @Size(max = 20)
    private String baseCalculo;

    @NotBlank
    @Size(max = 20)
    private String metodoCalculo;

    @NotBlank
    @Size(max = 20)
    private String frecuenciaCapitalizacion;

    @NotNull
    private LocalDateTime fechaInicioVigencia;

    private LocalDateTime fechaFinVigencia;

    @Valid
    private List<@NotNull TasaPlazoDTO> plazos;

    @Valid
    private List<@NotNull TasaSaldoDTO> saldos;
}
