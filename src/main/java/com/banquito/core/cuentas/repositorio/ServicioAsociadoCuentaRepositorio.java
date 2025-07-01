package com.banquito.core.cuentas.repositorio;

import com.banquito.core.cuentas.modelo.ServicioAsociadoCuenta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioAsociadoCuentaRepositorio extends MongoRepository<ServicioAsociadoCuenta, String> {
}
