package com.banquito.core.cuentas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ComisionCargoCuentaRequestDTO {
    @NotBlank(message = "El ID de comisión/cargo es obligatorio")
    private String comisionCargoId;

    @NotBlank(message = "El ID de cuenta cliente es obligatorio")
    private String cuentaClienteId;
}