package br.api.uno.estoque.service;

import br.api.uno.estoque.model.Estoque;
import br.api.uno.estoque.model.EstoqueDTO;
import br.api.uno.estoque.model.exceptions.EstoqueAlreadyRegisteredException;
import br.api.uno.estoque.model.exceptions.EstoqueNotFoundException;
import br.api.uno.estoque.repository.EstoqueRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstoqueService {
    private final EstoqueRepository repository;

    public EstoqueService(EstoqueRepository repository) {
        this.repository = repository;
    }

    public void cadastraEstoque(EstoqueDTO dto) {
        if (repository.existsByNome(dto.nome())) {
            throw new EstoqueAlreadyRegisteredException(String.format("Estoque %s já cadastrado!", dto.nome()));
        }

        Estoque estoque = new Estoque(
                null,
                dto.nome(),
                null
        );

        repository.save(estoque);
    }

    public List<EstoqueDTO> listarEstoques() {
        List<Estoque> estoques = repository.findAll();
        List<EstoqueDTO> dtos = new ArrayList<>();

        for (Estoque estoque : estoques) {
            EstoqueDTO dto = new EstoqueDTO(
                    estoque.getId(),
                    estoque.getNome()
            );

            dtos.add(dto);
        }

        return dtos;
    }

    public Estoque buscarEstoque(String nome) {
        return repository.findByNome(nome).orElseThrow(() -> new EstoqueNotFoundException(String.format("Estoque %s não encontrado!", nome)));
    }
}
