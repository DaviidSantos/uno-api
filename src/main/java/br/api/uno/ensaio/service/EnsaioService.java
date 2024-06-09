package br.api.uno.ensaio.service;

import br.api.uno.ensaio.model.Ensaio;
import br.api.uno.ensaio.model.EnsaioDTO;
import br.api.uno.ensaio.model.exceptions.EnsaioNotFoundException;
import br.api.uno.ensaio.repository.EnsaioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnsaioService {
    private final EnsaioRepository repository;

    public EnsaioService(EnsaioRepository repository) {
        this.repository = repository;
    }

    public void cadastrarEnsaio(EnsaioDTO dto) {
        Ensaio ensaio = new Ensaio(
                null,
                dto.nome(),
                null
        );

        repository.save(ensaio);
    }

    public List<EnsaioDTO> listarEnsaios() {
        List<Ensaio> ensaios = repository.findAll();
        List<EnsaioDTO> dtos = new ArrayList<>();

        for (Ensaio ensaio : ensaios) {
            dtos.add(new EnsaioDTO(
                    ensaio.getId(),
                    ensaio.getNome()
            ));
        }

        return dtos;
    }

    public EnsaioDTO buscarEnsaioPorNome(String nome) {
        Ensaio ensaio = repository.findByNome(nome).orElseThrow(() -> new EnsaioNotFoundException(String.format("Ensaio %s não encontrado!", nome)));

        EnsaioDTO dto = new EnsaioDTO(
                ensaio.getId(),
                ensaio.getNome()
        );

        return dto;
    }
}
