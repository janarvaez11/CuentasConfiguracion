package com.banquito.core.cuentas.modelo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "tipos_cuentas")
public class TipoCuenta {

    @Id
    private String id;

    private String idMoneda;

    @Indexed(name = "idx_tasa_defecto", unique = false)
    private String idTasaInteresPorDefecto;

    private String nombre;
    private String descripcion;
    private String tipoCliente; // p.ej. "PERSONA","EMPRESA","AMBOS"

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private String estado; // "ACTIVO","INACTIVO"
    private Integer version;

    public TipoCuenta(String id) {
        this.id = id;
    }

    public static final String ESTADO_ACTIVO = "ACTIVO";
    public static final String ESTADO_INACTIVO = "INACTIVO";

}
