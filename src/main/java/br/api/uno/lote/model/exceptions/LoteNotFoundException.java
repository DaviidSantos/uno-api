package br.api.uno.lote.model.exceptions;

public class LoteNotFoundException extends RuntimeException {
    public LoteNotFoundException(String msg) {
        super(msg);
    }
}
