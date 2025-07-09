
package com.banquito.core.cuentas.excepciones;

/**
 * Excepción que representa una violación de reglas de negocio
 * o condiciones de operación no permitidas.
 */
public class OperacionNoPermitidaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construye la excepción con un mensaje descriptivo.
     *
     * @param message descripción del error de negocio
     */
    public OperacionNoPermitidaException(String message) {
        super(message);
    }
}
