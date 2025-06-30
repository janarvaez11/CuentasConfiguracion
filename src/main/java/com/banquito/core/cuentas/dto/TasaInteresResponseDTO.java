// src/main/java/com/banquito/core/cuentas/dto/TasaInteresResponseDTO.java
package com.banquito.core.cuentas.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TasaInteresResponseDTO {
    private String id;
    private String baseCalculo;
    private String metodoCalculo;
    private String frecuenciaCapitalizacion;
    private LocalDateTime fechaInicioVigencia;
    private LocalDateTime fechaFinVigencia;
    private String estado;
    private Integer version;
    private List<TasaPlazoDTO> plazos;
    private List<TasaSaldoDTO> saldos;
}
