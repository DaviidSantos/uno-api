package br.api.uno.lote.model.exceptions;

public class NotaFiscalAlreadyRegisteredException extends RuntimeException {
    public NotaFiscalAlreadyRegisteredException(String message) {
        super(message);
    }
}
