package com.banquito.core.cuentas.repositorio;

import com.banquito.core.cuentas.modelo.TipoCuenta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoCuentaRepositorio extends MongoRepository<TipoCuenta, String> {
}
