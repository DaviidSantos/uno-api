package br.api.uno.solicitante.service;

import br.api.uno.solicitante.model.Solicitante;
import br.api.uno.solicitante.model.SolicitanteDTO;
import br.api.uno.solicitante.model.exceptions.CnpjAlreadyRegisteredException;
import br.api.uno.solicitante.repository.SolicitanteRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SolicitanteService {
    private final SolicitanteRepository repository;

    public SolicitanteService(SolicitanteRepository repository) {
        this.repository = repository;
    }

    public UUID cadastrarSolicitante(SolicitanteDTO dto) {
        if(repository.existsByCnpj(dto.cnpj())) {
            throw new CnpjAlreadyRegisteredException(String.format("Erro ao cadastrar solicitante: CNPJ %s já está cadastrado!", dto.cnpj()));
        }
        
        Solicitante solicitante = new Solicitante(dto.cnpj(), dto.nome(), dto.telefone(), dto.email(), dto.endereco(), dto.cidade(), dto.estado());

        solicitante = repository.save(solicitante);
        return solicitante.getId();
    }
}
