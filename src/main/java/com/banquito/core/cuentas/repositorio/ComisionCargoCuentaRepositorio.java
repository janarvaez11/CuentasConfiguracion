package com.banquito.core.cuentas.repositorio;

import com.banquito.core.cuentas.modelo.ComisionCargoCuenta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComisionCargoCuentaRepositorio extends MongoRepository<ComisionCargoCuenta, String> {
    /** Evita duplicados activos */
    boolean existsByComisionCargoIdAndCuentaClienteIdAndEstado(
            String comisionCargoId,
            String cuentaClienteId,
            String estado);

    /** Lista asignaciones activas de una cuenta */
    List<ComisionCargoCuenta> findByCuentaClienteIdAndEstado(
            String cuentaClienteId,
            String estado);
}