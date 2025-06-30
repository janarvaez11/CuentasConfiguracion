package com.banquito.core.cuentas.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TipoCuentaDTO {
    private String id;
    private String idMoneda;
    private String idTasaInteresPorDefecto;
    private String nombre;
    private String descripcion;
    private String requisitosApertura;
    private String tipoCliente;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private String estado;
    private Integer version;

    /** Constructor con todos los campos */
    public TipoCuentaDTO(
        String id,
        String idMoneda,
        String idTasaInteresPorDefecto,
        String nombre,
        String descripcion,
        String requisitosApertura,
        String tipoCliente,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion,
        String estado,
        Integer version
    ) {
        this.id = id;
        this.idMoneda = idMoneda;
        this.idTasaInteresPorDefecto = idTasaInteresPorDefecto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.requisitosApertura = requisitosApertura;
        this.tipoCliente = tipoCliente;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.estado = estado;
        this.version = version;
    }
}
