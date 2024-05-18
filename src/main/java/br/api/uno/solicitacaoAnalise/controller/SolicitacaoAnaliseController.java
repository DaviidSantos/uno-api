package br.api.uno.solicitacaoAnalise.controller;

import br.api.uno.solicitacaoAnalise.model.SolicitacaoAnaliseDTO;
import br.api.uno.solicitacaoAnalise.service.SolicitacaoAnaliseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        String idSa = service.cadastrarSolicitacaoAnalise(dto);

        URI uri = uriBuilder.path("/api/v1/solicitacao-analise").queryParam("id_sa", "{id_sa}").buildAndExpand(idSa).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<SolicitacaoAnaliseDTO> buscarSolicitacaoAnalisePorIdSa(@RequestParam String id_sa) {
        SolicitacaoAnaliseDTO dto = service.buscarSolicitacaoAnalisePorIdSa(id_sa);

        return ResponseEntity.ok(dto);
    }
}
