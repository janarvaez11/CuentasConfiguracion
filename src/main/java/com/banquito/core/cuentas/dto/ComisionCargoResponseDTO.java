package com.banquito.core.cuentas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ComisionCargoResponseDTO {
private String id;
private String tipoComision;
private String nombre;
private String baseCalculo;
private BigDecimal monto;
private String frecuencia;
private String servicioId;
private String descripcion;
private String estado;
private LocalDateTime fechaCreacion;
private LocalDateTime fechaModificacion;
private Long version;
}