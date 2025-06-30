package com.banquito.core.cuentas.dto;

import lombok.Data;

@Data
public class TipoCuentaResponseDTO {
    private String id;
    private String nombre;
    private String descripcion;
    private String tipoCliente;
    private String estado;
    private Integer version;
}