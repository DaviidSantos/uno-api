package br.api.uno.estoque.service;

import br.api.uno.estoque.model.Estoque;
import br.api.uno.estoque.model.EstoqueDTO;
import br.api.uno.estoque.model.exceptions.EstoqueAlreadyRegisteredException;
import br.api.uno.estoque.model.exceptions.EstoqueNotFoundException;
import br.api.uno.estoque.repository.EstoqueRepository;
import br.api.uno.solicitante.model.Solicitante;
import br.api.uno.solicitante.model.SolicitanteDTO;
import br.api.uno.solicitante.service.SolicitanteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstoqueService {
    private final EstoqueRepository repository;

    private final SolicitanteService solicitanteService;

    public EstoqueService(EstoqueRepository repository, SolicitanteService solicitanteService) {
        this.repository = repository;
        this.solicitanteService = solicitanteService;
    }

    public void cadastraEstoque(EstoqueDTO dto) {
        SolicitanteDTO solicitanteDTO = solicitanteService.buscarSolicitantePorCnpj(dto.solicitante().cnpj());

        Solicitante solicitante = new Solicitante(
                solicitanteDTO.id(),
                solicitanteDTO.cnpj(),
                solicitanteDTO.nome(),
                solicitanteDTO.telefone(),
                solicitanteDTO.email(),
                solicitanteDTO.endereco(),
                solicitanteDTO.cidade(),
                solicitanteDTO.estado(),
                null,
                null
        );

        if (repository.existsByNome(dto.nome())) {
            throw new EstoqueAlreadyRegisteredException(String.format("Estoque %s já cadastrado!", dto.nome()));
        }

        Estoque estoque = new Estoque(
                null,
                dto.nome(),
                solicitante,
                null
        );

        repository.save(estoque);
    }

    public List<EstoqueDTO> listarEstoques() {
        List<Estoque> estoques = repository.findAll();
        List<EstoqueDTO> dtos = new ArrayList<>();

        for (Estoque estoque : estoques) {
            SolicitanteDTO solicitanteDTO = new SolicitanteDTO(
                    estoque.getSolicitante().getId(),
                    estoque.getSolicitante().getCnpj(),
                    estoque.getSolicitante().getNome(),
                    estoque.getSolicitante().getTelefone(),
                    estoque.getSolicitante().getEmail(),
                    estoque.getSolicitante().getEndereco(),
                    estoque.getSolicitante().getCidade(),
                    estoque.getSolicitante().getEstado()
            );

            EstoqueDTO dto = new EstoqueDTO(
                    estoque.getId(),
                    estoque.getNome(),
                    solicitanteDTO
            );

            dtos.add(dto);
        }

        return dtos;
    }

    public Estoque buscarEstoque(String nome) {
        return repository.findByNome(nome).orElseThrow(() -> new EstoqueNotFoundException(String.format("Estoque %s não encontrado!", nome)));
    }
}
