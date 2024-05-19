package br.api.uno.estoque.controller;

import br.api.uno.estoque.model.EstoqueDTO;
import br.api.uno.estoque.service.EstoqueService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estoque")
public class EstoqueController {
    private final EstoqueService service;

    public EstoqueController(EstoqueService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity cadastrarEstoque(@RequestBody @Valid EstoqueDTO dto) {
        service.cadastraEstoque(dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<EstoqueDTO>> listarEstoques() {
        List<EstoqueDTO> list = service.listarEstoques();
        return ResponseEntity.ok().body(list);
    }
}
