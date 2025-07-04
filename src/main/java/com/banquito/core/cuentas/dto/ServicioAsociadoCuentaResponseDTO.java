package com.banquito.core.cuentas.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServicioAsociadoCuentaResponseDTO {

    private String id;
    private String servicioId;
    private String cuentaClienteId;
    private LocalDateTime fechaAsignacion;
    private String estado;
    private Long version;
}
