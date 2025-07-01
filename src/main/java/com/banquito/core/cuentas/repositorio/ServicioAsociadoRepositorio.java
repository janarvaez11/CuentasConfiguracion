package com.banquito.core.cuentas.repositorio;

import com.banquito.core.cuentas.modelo.ServicioAsociado;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioAsociadoRepositorio extends MongoRepository<ServicioAsociado, String> {
}
