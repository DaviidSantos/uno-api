package br.api.uno.lote.controller;

import br.api.uno.lote.model.LoteDTO;
import br.api.uno.lote.service.LoteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/lote")
public class LoteController {
    private final LoteService service;

    public LoteController(LoteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity cadastrarLote(@RequestBody @Valid LoteDTO dto, UriComponentsBuilder uriBuilder) {
        UUID id = service.cadastrarLote(dto);

        URI uri = uriBuilder.path("/api/v1/lote/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<LoteDTO> buscarLote(@PathVariable UUID id) {
        LoteDTO dto = service.buscarLotePorId(id);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/solicitacao-analise")
    public ResponseEntity<List<LoteDTO>> buscarLotePorSolicitacaoAnalise(@RequestParam String idSa) {
        List<LoteDTO> dtos = service.buscarLotesPorSolicitacaoAnalise(idSa);
        return ResponseEntity.ok(dtos);
    }
}
