package com.banquito.core.cuentas.modelo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "tasas_intereses")
@CompoundIndexes({
        @CompoundIndex(name = "idx_base_metodo_freq", def = "{'baseCalculo':1,'metodoCalculo':1,'frecuenciaCapitalizacion':1}", unique = true)
})
public class TasaInteres {

    @Id
    private String id;

    private String baseCalculo;
    private String metodoCalculo;
    private String frecuenciaCapitalizacion;

    private LocalDateTime fechaInicioVigencia;
    private LocalDateTime fechaFinVigencia;

    private LocalDateTime fechaModificacion;


    private String estado;
    private Integer version;

    // listas embebidas en el mismo documento
    private List<TasaPlazo> plazos;
    private List<TasaSaldo> saldos;

    public TasaInteres(String id) {
        this.id = id;
    }

    public static final String BASE_30_360 = "BASE_30_360";
    public static final String BASE_31_365 = "BASE_31_365";
    public static final String METODO_SALDO_DIARIO = "SALDO_DIARIO";
    public static final String METODO_PROMEDIO = "SALDO_PROMEDIO";
    public static final String FREQ_DIARIA = "DIARIA";
    public static final String FREQ_MENSUAL = "MENSUAL";
    public static final String FREQ_SEMESTRAL = "SEMESTRAL";
    public static final String FREQ_ANUAL = "ANUAL";
    public static final String ESTADO_ACTIVO = "ACTIVO";
    public static final String ESTADO_INACTIVO = "INACTIVO";
}
