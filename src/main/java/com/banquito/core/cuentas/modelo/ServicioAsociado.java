package com.banquito.core.cuentas.modelo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor
@Document(collection = "servicios_asociados")
public class ServicioAsociado {
    @Id
    private String id;
    
    @Indexed(name = "idx_servicio_nombre", unique = true)
    private String nombre;
    private String descripcion;
    private String estado;            // ACTIVO / INACTIVO
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private Long version;
    
    public ServicioAsociado(String id) { this.id = id; }

    // constantes de estado
    public static final String ESTADO_ACTIVO   = "ACTIVO";
    public static final String ESTADO_INACTIVO = "INACTIVO";
}
