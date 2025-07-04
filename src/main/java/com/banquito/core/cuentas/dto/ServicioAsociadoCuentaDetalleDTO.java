package com.banquito.core.cuentas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ServicioAsociadoCuentaDetalleDTO {

    private String id;                  // ID de la asignación
    private String nombreServicio;      // Nombre legible del servicio
    private String descripcionServicio; // Descripción del servicio
    private LocalDateTime fechaAsignacion;
    private String estado;
    private Long version;
}
