package br.api.uno.reagenteAnalise.controller;

import br.api.uno.reagenteAnalise.model.ReagenteAnaliseDTO;
import br.api.uno.reagenteAnalise.service.ReagenteAnaliseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/reagente-analise")
public class ReagenteAnaliseController {
    private final ReagenteAnaliseService service;

    public ReagenteAnaliseController(ReagenteAnaliseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity cadastrarReagenteUsado(@RequestBody ReagenteAnaliseDTO reagenteAnalise) {
        service.cadastrarReagenteUsado(reagenteAnalise);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ReagenteAnaliseDTO>> listarReagentesPorAnalise(@RequestParam UUID analise) {
        List<ReagenteAnaliseDTO> dtos = service.listarReagentesPorAnalise(analise);
        return ResponseEntity.ok().body(dtos);
    }
}
