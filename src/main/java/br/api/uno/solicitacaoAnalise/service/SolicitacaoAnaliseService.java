package br.api.uno.solicitacaoAnalise.service;

import br.api.uno.solicitacaoAnalise.model.SolicitacaoAnalise;
import br.api.uno.solicitacaoAnalise.model.SolicitacaoAnaliseDTO;
import br.api.uno.solicitacaoAnalise.model.enums.TipoAnalise;
import br.api.uno.utils.exceptions.InvalidFieldValueException;
import br.api.uno.utils.exceptions.MissingFieldException;
import br.api.uno.solicitacaoAnalise.repository.SolicitacaoAnaliseRepository;
import br.api.uno.solicitante.model.Solicitante;
import br.api.uno.solicitante.model.SolicitanteDTO;
import br.api.uno.solicitante.service.SolicitanteService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.UUID;

@Service
public class SolicitacaoAnaliseService {
    private final SolicitacaoAnaliseRepository repository;
    private final SolicitanteService solicitanteService;

    public SolicitacaoAnaliseService(SolicitacaoAnaliseRepository repository, SolicitanteService solicitanteService) {
        this.repository = repository;
        this.solicitanteService = solicitanteService;
    }

    public UUID cadastrarSolicitacaoAnalise(SolicitacaoAnaliseDTO dto) {
        if(dto.solicitante().cnpj() == null) {
            throw new MissingFieldException("Campo CNPJ do Solicitante é obrigatório!");
        }

        if(dto.prazoAcordado().isBefore(LocalDate.now())) {
            throw new InvalidFieldValueException("O prazo acordado deve ser após a data atual!");
        }

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
                null
        );

        int anoAtual = Year.now().getValue();
        int numeroSa = repository.countByIdSaEndingWith(anoAtual) + 1;

        String idSa = String.format("SA-%04d/%d", numeroSa, anoAtual);

        SolicitacaoAnalise solicitacaoAnalise = new SolicitacaoAnalise(
                null,
                idSa,
                TipoAnalise.valueOf(dto.tipoAnalise()),
                dto.prazoAcordado(),
                null,
                dto.descricaoProjeto(),
                solicitante,
                null
        );

        return repository.save(solicitacaoAnalise).getId();
    }

}
