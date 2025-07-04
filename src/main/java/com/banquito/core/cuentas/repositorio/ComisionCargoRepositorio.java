package com.banquito.core.cuentas.repositorio;

import com.banquito.core.cuentas.modelo.ComisionCargo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComisionCargoRepositorio extends MongoRepository<ComisionCargo, String> {
    /** Busca todas las comisiones/cargos por estado */
    List<ComisionCargo> findByEstado(String estado);

    /** Valida duplicados activos (mismo tipo y servicio) */
    boolean existsByTipoComisionAndServicioIdAndEstado(String tipoComision, String servicioId, String estado);
}