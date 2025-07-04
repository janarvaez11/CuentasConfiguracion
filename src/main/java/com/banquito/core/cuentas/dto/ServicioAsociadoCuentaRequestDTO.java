package com.banquito.core.cuentas.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServicioAsociadoCuentaRequestDTO {

    @NotBlank(message = "El ID del servicio es obligatorio")
    private String servicioId;

    @NotBlank(message = "El ID de la cuenta cliente es obligatorio")
    private String cuentaClienteId;
}
