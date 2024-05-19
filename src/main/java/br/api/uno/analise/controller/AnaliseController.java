package br.api.uno.analise.controller;

import br.api.uno.analise.model.AnaliseDTO;
import br.api.uno.analise.service.AnaliseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/analise")
public class AnaliseController {
    private final AnaliseService service;

    public AnaliseController(AnaliseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity cadastrarAnalise(@RequestBody @Valid AnaliseDTO analise, UriComponentsBuilder uriBuilder) {
        UUID id = service.cadastrarAnalise(analise);

        URI uri = uriBuilder.path("/api/v1/analise/{id}").build(id);

        return ResponseEntity.created(uri).build();
    }
}
