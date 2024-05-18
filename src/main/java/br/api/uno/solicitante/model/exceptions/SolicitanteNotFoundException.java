package br.api.uno.solicitante.model.exceptions;

public class SolicitanteNotFoundException extends RuntimeException {
    public SolicitanteNotFoundException(String msg) {
        super(msg);
    }
}
