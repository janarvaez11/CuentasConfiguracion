package com.banquito.core.cuentas.repositorio;

import com.banquito.core.cuentas.modelo.ServicioAsociado;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioAsociadoRepositorio extends MongoRepository<ServicioAsociado, String> {

    List<ServicioAsociado> findByEstado(String estado);
}
