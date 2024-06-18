package br.api.uno.reagente.service;

import br.api.uno.estoque.model.Estoque;
import br.api.uno.estoque.model.EstoqueDTO;
import br.api.uno.estoque.service.EstoqueService;
import br.api.uno.reagente.exceptions.ReagenteNotFoundException;
import br.api.uno.reagente.model.Reagente;
import br.api.uno.reagente.model.ReagenteDTO;
import br.api.uno.reagente.repository.ReagenteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReagenteService {
    private final ReagenteRepository repository;

    private final EstoqueService estoqueService;

    public ReagenteService(ReagenteRepository repository, EstoqueService estoqueService) {
        this.repository = repository;
        this.estoqueService = estoqueService;
    }

    public void cadastrarReagente(ReagenteDTO dto) {
        Estoque estoque = estoqueService.buscarEstoque(dto.estoque().nome());

        Reagente reagente = new Reagente(
                null,
                dto.nome(),
                dto.fornecedor(),
                dto.descricao(),
                dto.quantidade(),
                dto.unidade(),
                estoque
        );

        repository.save(reagente);
    }
    
    public List<ReagenteDTO> buscarReagentePorEstoque(String nome) {
        Estoque estoque = estoqueService.buscarEstoque(nome);
        
        List<Reagente> reagentes = repository.findAllByEstoque(estoque);
        List<ReagenteDTO> dtos = new ArrayList<>();
        
        for (Reagente reagente : reagentes) {
            ReagenteDTO reagenteDTO = entityToDTO(reagente);

            dtos.add(reagenteDTO);
        }
        
        return dtos;
    }

    public Reagente buscarReagentePorId(UUID id) {
        Reagente reagente = repository.findById(id).orElseThrow(() -> new ReagenteNotFoundException("Reagente não encontrado!"));

        return reagente;
    }

    public void retirarReagente(UUID id, double quantidade) {
        Reagente reagente = repository.findById(id).orElseThrow(() -> new ReagenteNotFoundException("Reagente não encontrado!"));

        reagente.retirarReagente(quantidade);

        repository.save(reagente);
    }

    private static ReagenteDTO entityToDTO(Reagente reagente) {
        EstoqueDTO estoqueDTO = new EstoqueDTO(
                reagente.getEstoque().getId(),
                reagente.getEstoque().getNome(),
                null
        );

        return new ReagenteDTO(
                reagente.getId(),
                reagente.getNome(),
                reagente.getFornecedor(),
                reagente.getDescricao(),
                reagente.getUnidade(),
                reagente.getQuantidade(),
                estoqueDTO
        );
    }
}
