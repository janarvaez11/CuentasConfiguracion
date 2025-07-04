package com.banquito.core.cuentas.modelo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor
@Document(collection = "comisiones_cargos")
@CompoundIndexes({
    @CompoundIndex(name = "idx_tipserv", def = "{'tipoComision':1,'servicioId':1}", unique = true)
})
public class ComisionCargo {
    @Id
    private String id;

    private String tipoComision;      // TRANSACCION / SERVICIO / PERIODICO
    private String servicioId;        // id de ServicioAsociado
    private String nombre;
    private String baseCalculo;       // FIJO / PORCENTAJE
    private BigDecimal monto;
    private String frecuencia;        // MENSUAL / TRIMESTRAL / ANUAL
    private String estado;            // ACTIVO / INACTIVO
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private Long version;

    // Exenciones embebidas
    private List<ExencionCuenta> exenciones;

    public ComisionCargo(String id) { this.id = id; }

    // constantes
    public static final String TIPO_TRANSACCION = "TRANSACCION";
    public static final String TIPO_SERVICIO     = "SERVICIO";
    public static final String TIPO_PERIODICO    = "PERIODICO";

    public static final String BASE_FIJO        = "FIJO";
    public static final String BASE_PORCENTAJE  = "PORCENTAJE";

    public static final String FREQ_MENSUAL     = "MENSUAL";
    public static final String FREQ_TRIMESTRAL = "TRIMESTRAL";
    public static final String FREQ_ANUAL       = "ANUAL";

    public static final String ESTADO_ACTIVO    = "ACTIVO";
    public static final String ESTADO_INACTIVO  = "INACTIVO";


}
