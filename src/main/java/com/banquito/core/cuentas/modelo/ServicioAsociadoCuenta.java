package com.banquito.core.cuentas.modelo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.CompoundIndex;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor
@Document(collection = "servicios_asociados_cuentas")
@CompoundIndex(name = "idx_serv_cuenta", def = "{'servicioId':1,'cuentaClienteId':1}", unique = true)
public class ServicioAsociadoCuenta {
    @Id
    private String id;

    private String servicioId;        // referenciamos por id de ServicioAsociado
    private String cuentaClienteId;   // id de la cuenta cliente en el módulo transaccional
    private LocalDateTime fechaAsignacion;
    private String estado;            // ACTIVO / INACTIVO
    private Long version;

    public ServicioAsociadoCuenta(String id) { this.id = id; }

    public static final String ESTADO_ACTIVO   = "ACTIVO";
    public static final String ESTADO_INACTIVO = "INACTIVO";
}
