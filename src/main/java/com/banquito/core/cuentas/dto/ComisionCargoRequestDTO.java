package com.banquito.core.cuentas.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ComisionCargoRequestDTO {
    @NotBlank(message = "El tipo de comisión es obligatorio")
    private String tipoComision; // TRANSACCION, SERVICIO, PERIODICO

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La base de cálculo es obligatoria")
    private String baseCalculo; // FIJO, PORCENTAJE

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.00", inclusive = false, message = "El monto debe ser mayor que cero")
    private BigDecimal monto;

    @NotBlank(message = "La frecuencia es obligatoria")
    private String frecuencia; // DIARIA, MENSUAL, ANUAL o UNICA_VEZ

    @NotBlank(message = "El ID de servicio es obligatorio")
    private String servicioId;

    private String descripcion;
}