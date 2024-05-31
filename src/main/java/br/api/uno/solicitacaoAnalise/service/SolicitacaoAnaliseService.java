package br.api.uno.solicitacaoAnalise.service;

import br.api.uno.solicitacaoAnalise.exceptions.SolicitacaoAnaliseNotFoundException;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class SolicitacaoAnaliseService {
    private final SolicitacaoAnaliseRepository repository;
    private final SolicitanteService solicitanteService;

    public SolicitacaoAnaliseService(SolicitacaoAnaliseRepository repository, SolicitanteService solicitanteService) {
        this.repository = repository;
        this.solicitanteService = solicitanteService;
    }

    public String cadastrarSolicitacaoAnalise(SolicitacaoAnaliseDTO dto) {
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
                dto.nomeProjeto(),
                TipoAnalise.valueOf(dto.tipoAnalise()),
                dto.prazoAcordado(),
                null,
                dto.descricaoProjeto(),
                solicitante,
                null
        );

        repository.save(solicitacaoAnalise);

        return idSa;
    }

    public SolicitacaoAnaliseDTO buscarSolicitacaoAnalisePorIdSa(String idSa) {
        SolicitacaoAnalise solicitacaoAnalise = repository.findByIdSa(idSa).orElseThrow(() -> new SolicitacaoAnaliseNotFoundException(String.format("Solicitação de Análise com id %s não foi encontrada!", idSa)));
        SolicitacaoAnaliseDTO dto = entityToDTO(solicitacaoAnalise);

        return dto;
    }

    public List<SolicitacaoAnaliseDTO> buscarSolicitacaoAnalisePorSolicitante(String cnpj) {
        SolicitanteDTO solicitanteDTO = solicitanteService.buscarSolicitantePorCnpj(cnpj);
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

        List<SolicitacaoAnalise> solicitacoesAnalise = repository.findAllBySolicitante(solicitante);
        List<SolicitacaoAnaliseDTO> dtos = new ArrayList<>();

        for (SolicitacaoAnalise solicitacaoAnalise : solicitacoesAnalise) {
            SolicitacaoAnaliseDTO dto = entityToDTO(solicitacaoAnalise);
            dtos.add(dto);
        }

        return dtos;
    }

    public List<SolicitacaoAnaliseDTO> listarSolicitacoesAnalise() {
        List<SolicitacaoAnalise> solicitacoesAnalise = repository.findAll();
        List<SolicitacaoAnaliseDTO> dtos = new ArrayList<>();

        for (SolicitacaoAnalise solicitacaoAnalise : solicitacoesAnalise) {
            SolicitacaoAnaliseDTO dto = entityToDTO(solicitacaoAnalise);
            dtos.add(dto);
        }

        return dtos;
    }

    public void atualizarSolicitatacaoAnalise(SolicitacaoAnaliseDTO dto) {
        SolicitacaoAnalise solicitacaoAnalise = repository.findByIdSa(dto.idSa()).orElseThrow(() -> new SolicitacaoAnaliseNotFoundException(String.format("Solicitação de Análise com id %s não foi encontrada!", dto.idSa())));

        setSolicitacaoAnalise(solicitacaoAnalise, dto);

        repository.save(solicitacaoAnalise);
    }

    private void setSolicitacaoAnalise(SolicitacaoAnalise solicitacaoAnalise, SolicitacaoAnaliseDTO dto) {
        solicitacaoAnalise.setNomeProjeto(dto.nomeProjeto());
        solicitacaoAnalise.setTipoAnalise(TipoAnalise.valueOf(dto.tipoAnalise()));
        solicitacaoAnalise.setConclusaoProjeto(dto.conclusaoProjeto());
        solicitacaoAnalise.setDescricaoProjeto(dto.descricaoProjeto());
    }

    private SolicitacaoAnaliseDTO entityToDTO(SolicitacaoAnalise solicitacaoAnalise) {
        SolicitanteDTO solicitanteDTO = new SolicitanteDTO(
                solicitacaoAnalise.getSolicitante().getId(),
                solicitacaoAnalise.getSolicitante().getCnpj(),
                solicitacaoAnalise.getSolicitante().getNome(),
                solicitacaoAnalise.getSolicitante().getTelefone(),
                solicitacaoAnalise.getSolicitante().getEmail(),
                solicitacaoAnalise.getSolicitante().getEndereco(),
                solicitacaoAnalise.getSolicitante().getCidade(),
                solicitacaoAnalise.getSolicitante().getEstado()
        );

        return new SolicitacaoAnaliseDTO(
                solicitacaoAnalise.getId(),
                solicitacaoAnalise.getIdSa(),
                solicitacaoAnalise.getNomeProjeto(),
                solicitacaoAnalise.getTipoAnalise().toString(),
                solicitacaoAnalise.getPrazoAcordado(),
                solicitacaoAnalise.getConclusaoProjeto(),
                solicitacaoAnalise.getDescricaoProjeto(),
                solicitanteDTO
        );
    }
}
