package br.api.uno.utils;

import br.api.uno.ensaio.model.exceptions.EnsaioNotFoundException;
import br.api.uno.estoque.model.exceptions.EstoqueAlreadyRegisteredException;
import br.api.uno.lote.model.exceptions.LoteNotFoundException;
import br.api.uno.lote.model.exceptions.NotaFiscalAlreadyRegisteredException;
import br.api.uno.solicitacaoAnalise.exceptions.SolicitacaoAnaliseNotFoundException;
import br.api.uno.solicitante.model.exceptions.CnpjAlreadyRegisteredException;
import br.api.uno.solicitante.model.exceptions.SolicitanteNotFoundException;
import br.api.uno.utils.exceptions.InvalidFieldValueException;
import br.api.uno.utils.exceptions.MissingFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CnpjAlreadyRegisteredException.class)
    protected Map<String, String> handleCnpjAlreadyRegisteredException(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("erro", ex.getMessage());

        return error;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(NotaFiscalAlreadyRegisteredException.class)
    protected Map<String, String> handleNotaFiscalAlreadyRegisteredException(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("erro", ex.getMessage());

        return error;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EstoqueAlreadyRegisteredException.class)
    protected Map<String, String> handleEstoqueAlreadyRegisteredException(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("erro", ex.getMessage());

        return error;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SolicitanteNotFoundException.class)
    protected Map<String, String> handleSolicitanteNotFoundException(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("erro", ex.getMessage());

        return error;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SolicitacaoAnaliseNotFoundException.class)
    protected Map<String, String> handleSolicitacaoAnaliseNotFoundException(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("erro", ex.getMessage());

        return error;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(LoteNotFoundException.class)
    protected Map<String, String> handleLoteNotFoundException(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("erro", ex.getMessage());

        return error;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EnsaioNotFoundException.class)
    protected Map<String, String> handleEnsaioNotFoundException(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("erro", ex.getMessage());

        return error;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingFieldException.class)
    protected Map<String, String> handleMissingFieldException(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("erro", ex.getMessage());

        return error;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFieldValueException.class)
    protected Map<String, String> handleInvalidFieldValueException(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("erro", ex.getMessage());

        return error;
    }


}
