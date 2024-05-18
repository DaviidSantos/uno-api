package br.api.uno.utils.exceptions;

public class MissingFieldException extends RuntimeException {
    public MissingFieldException(String msg) {
        super(msg);
    }
}
