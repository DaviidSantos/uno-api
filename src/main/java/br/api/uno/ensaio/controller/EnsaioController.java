package br.api.uno.ensaio.controller;

import br.api.uno.ensaio.model.EnsaioDTO;
import br.api.uno.ensaio.service.EnsaioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/ensaio")
public class EnsaioController {
    private final EnsaioService service;

    public EnsaioController(EnsaioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity cadastrarEnsaio(@RequestBody @Valid EnsaioDTO ensaio) {
        service.cadastrarEnsaio(ensaio);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<EnsaioDTO>> listarEnsaios() {
        return ResponseEntity.ok(service.listarEnsaios());
    }
}
