package com.banquito.core.cuentas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ComisionCargoCuentaResponseDTO {
    private String id;
    private String comisionCargoId;
    private String cuentaClienteId;
    private LocalDateTime fechaAsignacion;
    private String estado;
    private Long version;
}