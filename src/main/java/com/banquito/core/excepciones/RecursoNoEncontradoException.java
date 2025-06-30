package com.banquito.core.excepciones;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecursoNoEncontradoException extends RuntimeException {
  public RecursoNoEncontradoException(String entidad, String id) {
    super(entidad + " con ID " + id + " no encontrado");
  }
}
