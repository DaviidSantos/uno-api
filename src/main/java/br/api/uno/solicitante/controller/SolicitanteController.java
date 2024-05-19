package br.api.uno.solicitante.controller;

import br.api.uno.solicitante.model.SolicitanteDTO;
import br.api.uno.solicitante.service.SolicitanteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
}
