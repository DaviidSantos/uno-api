package br.api.uno.solicitacaoAnalise.controller;

import br.api.uno.solicitacaoAnalise.model.SolicitacaoAnaliseDTO;
import br.api.uno.solicitacaoAnalise.service.SolicitacaoAnaliseService;
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
@RequestMapping("/api/v1/solicitacao-analise")
public class SolicitacaoAnaliseController {
    private final SolicitacaoAnaliseService service;

    public SolicitacaoAnaliseController(SolicitacaoAnaliseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity cadastrarSolicitacaoAnalise(@RequestBody @Valid SolicitacaoAnaliseDTO dto, UriComponentsBuilder uriBuilder) {
        UUID id = service.cadastrarSolicitacaoAnalise(dto);

        URI uri = uriBuilder.path("/api/v1/solicitacao-analise/{id}").build(id);

        return ResponseEntity.created(uri).build();
    }
}
