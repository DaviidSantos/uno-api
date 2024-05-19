package br.api.uno.estoque.model.exceptions;

public class EstoqueAlreadyRegisteredException extends RuntimeException {
    public EstoqueAlreadyRegisteredException(String msg) {
        super(msg);
    }
}
