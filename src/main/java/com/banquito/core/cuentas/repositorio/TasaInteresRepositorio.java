package com.banquito.core.cuentas.repositorio;

import com.banquito.core.cuentas.modelo.TasaInteres;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasaInteresRepositorio extends MongoRepository<TasaInteres, String> {
  // puedes añadir query methods aquí si los necesitas
}
