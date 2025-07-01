package com.banquito.core.cuentas.repositorio;

import com.banquito.core.cuentas.modelo.ComisionCargo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComisionCargoRepositorio extends MongoRepository<ComisionCargo, String> {
}
