package com.banquito.core.cuentas.repositorio;

import com.banquito.core.cuentas.modelo.ServicioAsociadoCuenta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioAsociadoCuentaRepositorio extends MongoRepository<ServicioAsociadoCuenta, String> {

    /** Para validar duplicados activos */
    boolean existsByServicioIdAndCuentaClienteIdAndEstado(
        String servicioId,
        String cuentaClienteId,
        String estado
    );

    /** Para listar sólo las asignaciones activas de una cuenta */
    List<ServicioAsociadoCuenta> findByCuentaClienteIdAndEstado(
        String cuentaClienteId,
        String estado
    );
}
