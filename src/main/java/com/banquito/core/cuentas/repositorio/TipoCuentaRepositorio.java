package com.banquito.core.cuentas.repositorio;

import com.banquito.core.cuentas.modelo.TipoCuenta;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoCuentaRepositorio extends MongoRepository<TipoCuenta, String> {
    List<TipoCuenta> findByEstado(String estado);
}
