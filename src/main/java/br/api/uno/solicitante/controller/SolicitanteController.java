package br.api.uno.solicitante.controller;

import br.api.uno.solicitante.model.SolicitanteDTO;
import br.api.uno.solicitante.model.exceptions.CnpjAlreadyRegisteredException;
import br.api.uno.solicitante.service.SolicitanteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/solicitante")
public class SolicitanteController {
    private final SolicitanteService service;

    public SolicitanteController(SolicitanteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity cadastrarSolicitante(@RequestBody @Valid SolicitanteDTO dto, UriComponentsBuilder uriBuilder) {
        UUID id = service.cadastrarSolicitante(dto);

        URI uri = uriBuilder.path("/solicitante/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<List<SolicitanteDTO>> listarSolicitantes() {
        List<SolicitanteDTO> dtos = service.listarSolicitantes();
        return ResponseEntity.ok().body(dtos);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CnpjAlreadyRegisteredException.class)
    protected Map<String, String> handleAlreadyRegisteredCnpj(RuntimeException ex, WebRequest request) {
        Map<String, String> error = new HashMap<>();
        error.put("erro", ex.getMessage());

        return error;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
