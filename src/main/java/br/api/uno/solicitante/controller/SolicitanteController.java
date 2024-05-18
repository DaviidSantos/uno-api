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

@RestController
@RequestMapping("/api/v1/solicitante")
public class SolicitanteController {
    private final SolicitanteService service;

    public SolicitanteController(SolicitanteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity cadastrarSolicitante(@RequestBody @Valid SolicitanteDTO dto, UriComponentsBuilder uriBuilder) {
        service.cadastrarSolicitante(dto);

        URI uri = uriBuilder.path("api/v1/solicitante").queryParam("cnpj", "{cnpj}").buildAndExpand(dto.cnpj()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/listagem")
    public ResponseEntity<List<SolicitanteDTO>> listarSolicitantes() {
        List<SolicitanteDTO> dtos = service.listarSolicitantes();
        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping
    public ResponseEntity<SolicitanteDTO> buscarSolicitantePorCnpj(@RequestParam String cnpj) {
        SolicitanteDTO obj = service.buscarSolicitantePorCnpj(cnpj);
        return ResponseEntity.ok().body(obj);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CnpjAlreadyRegisteredException.class)
    protected Map<String, String> handleAlreadyRegisteredCnpj(RuntimeException ex, WebRequest request) {
        Map<String, String> error = new HashMap<>();
        error.put("erro", ex.getMessage());

        return error;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CnpjAlreadyRegisteredException.class)
    protected Map<String, String> handleSolicitanteNotFound(RuntimeException ex, WebRequest request) {
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
