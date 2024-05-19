package br.api.uno.estoque.model.exceptions;

public class EstoqueNotFoundException extends RuntimeException {
    public EstoqueNotFoundException(String msg) {
        super(msg);
    }
}
