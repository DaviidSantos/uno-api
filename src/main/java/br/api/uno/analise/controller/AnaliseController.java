package br.api.uno.analise.controller;

import br.api.uno.analise.model.AnaliseDTO;
import br.api.uno.analise.service.AnaliseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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

    @GetMapping
    public ResponseEntity<List<AnaliseDTO>> listarAnalises() {
        List<AnaliseDTO> list = service.listarAnalises();
        return ResponseEntity.ok(list);
    }
}
