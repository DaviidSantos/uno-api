package br.api.uno.solicitante.model.exceptions;

public class CnpjAlreadyRegisteredException extends RuntimeException {
    public CnpjAlreadyRegisteredException(String message) {
        super(message);
    }
}
