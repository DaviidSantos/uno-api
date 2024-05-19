package br.api.uno.lote.service;

import br.api.uno.lote.model.Lote;
import br.api.uno.lote.model.LoteDTO;
import br.api.uno.lote.model.exceptions.NotaFiscalAlreadyRegisteredException;
import br.api.uno.lote.repository.LoteRepository;
import br.api.uno.solicitacaoAnalise.model.SolicitacaoAnalise;
import br.api.uno.solicitacaoAnalise.model.SolicitacaoAnaliseDTO;
import br.api.uno.solicitacaoAnalise.model.enums.TipoAnalise;
import br.api.uno.solicitacaoAnalise.service.SolicitacaoAnaliseService;
import br.api.uno.utils.exceptions.InvalidFieldValueException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class LoteService {
    private final LoteRepository repository;

    private final SolicitacaoAnaliseService solicitacaoAnaliseService;

    public LoteService(LoteRepository repository, SolicitacaoAnaliseService solicitacaoAnaliseService) {
        this.repository = repository;
        this.solicitacaoAnaliseService = solicitacaoAnaliseService;
    }

    public UUID cadastrarLote(LoteDTO dto) {
        if(repository.existsByNotaFiscal(dto.notaFiscal())) {
            throw new NotaFiscalAlreadyRegisteredException(String.format("Nota fiscal de número %s já está cadastrada!", dto.notaFiscal()));
        }

        if (dto.dataEntrada().isAfter(LocalDate.now())) {
            throw new InvalidFieldValueException("Data de entrada não pode ser no futuro!");
        }

        SolicitacaoAnaliseDTO solicitacaoAnaliseDTO = solicitacaoAnaliseService.buscarSolicitacaoAnalisePorIdSa(dto.solicitacaoAnalise().idSa());

        SolicitacaoAnalise solicitacaoAnalise = new SolicitacaoAnalise(
                solicitacaoAnaliseDTO.id(),
                solicitacaoAnaliseDTO.idSa(),
                solicitacaoAnaliseDTO.nomeProjeto(),
                TipoAnalise.valueOf(solicitacaoAnaliseDTO.tipoAnalise()),
                solicitacaoAnaliseDTO.prazoAcordado(),
                solicitacaoAnaliseDTO.conclusaoProjeto(),
                solicitacaoAnaliseDTO.descricaoProjeto(),
                null,
                null
        );

        Lote lote = new Lote(
                null,
                dto.amostra(),
                dto.notaFiscal(),
                dto.dataEntrada(),
                dto.dataValidade(),
                dto.descricao(),
                dto.quantidade(),
                solicitacaoAnalise,
                null
        );

        lote = repository.save(lote);
        return lote.getId();
    }
}
