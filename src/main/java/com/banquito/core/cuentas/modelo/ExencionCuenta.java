package com.banquito.core.cuentas.modelo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class ExencionCuenta {
    private String id; // id de la exención
    private String nombre;
    private String descripcion;
    private String estado; // ACTIVO / INACTIVO
    private Long version;

    public ExencionCuenta(String id) {
        this.id = id;
    }

}
