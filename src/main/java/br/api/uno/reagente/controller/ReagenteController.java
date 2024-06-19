package br.api.uno.reagente.controller;

import br.api.uno.reagente.model.Reagente;
import br.api.uno.reagente.model.ReagenteDTO;
import br.api.uno.reagente.service.ReagenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/reagente")
public class ReagenteController {
    private final ReagenteService service;

    public ReagenteController(ReagenteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity cadastrarReagente(@RequestBody ReagenteDTO dto) {
        service.cadastrarReagente(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ReagenteDTO>> listarReagentesPorEstoque(@RequestParam String estoque) {
        List<ReagenteDTO> dtos = service.buscarReagentePorEstoque(estoque);
        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping("{id}")
    public ResponseEntity<ReagenteDTO> buscarReagentePorId(@PathVariable String id) {
        Reagente reagente = service.buscarReagentePorId(UUID.fromString(id));
        ReagenteDTO dto = ReagenteService.entityToDTO(reagente);
        return ResponseEntity.ok().body(dto);
    }
}
