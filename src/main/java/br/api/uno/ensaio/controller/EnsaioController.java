package br.api.uno.ensaio.controller;

import br.api.uno.ensaio.model.EnsaioDTO;
import br.api.uno.ensaio.service.EnsaioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
