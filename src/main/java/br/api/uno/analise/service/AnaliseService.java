package br.api.uno.analise.service;

import br.api.uno.analise.model.Analise;
import br.api.uno.analise.model.AnaliseDTO;
import br.api.uno.analise.repository.AnaliseRepository;
import br.api.uno.ensaio.model.Ensaio;
import br.api.uno.ensaio.model.EnsaioDTO;
import br.api.uno.ensaio.service.EnsaioService;
import br.api.uno.lote.model.Lote;
import br.api.uno.lote.model.LoteDTO;
import br.api.uno.lote.service.LoteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AnaliseService {
    private final AnaliseRepository repository;
    private final LoteService loteService;
    private final EnsaioService ensaioService;

    public AnaliseService(AnaliseRepository repository, LoteService loteService, EnsaioService ensaioService) {
        this.repository = repository;
        this.loteService = loteService;
        this.ensaioService = ensaioService;
    }

    public UUID cadastrarAnalise(AnaliseDTO dto) {
        LoteDTO loteDTO = loteService.buscarLotePorId(dto.lote().id());
        EnsaioDTO ensaioDTO = ensaioService.buscarEnsaioPorNome(dto.ensaio().nome());

        Lote lote = new Lote(
                loteDTO.id(),
                loteDTO.amostra(),
                loteDTO.notaFiscal(),
                loteDTO.dataEntrada(),
                loteDTO.dataValidade(),
                loteDTO.descricao(),
                loteDTO.quantidade(),
                null,
                null
        );

        Ensaio ensaio = new Ensaio(
                ensaioDTO.id(),
                ensaioDTO.nome(),
                null
        );

        Analise analise = new Analise(
                null,
                dto.especificacao(),
                null,
                dto.unidade(),
                dto.observacao(),
                lote,
                ensaio
        );

        return repository.save(analise).getId();
    }

    public List<AnaliseDTO> listarAnalises() {
        List<Analise> analises = repository.findAll();

        List<AnaliseDTO> dtos = new ArrayList<>();

        for (Analise analise : analises) {
            AnaliseDTO dto = entityToDTO(analise);
            dtos.add(dto);
        }

        return dtos;
    }

    private AnaliseDTO entityToDTO(Analise analise) {
        EnsaioDTO ensaioDTO = new EnsaioDTO(
                analise.getEnsaio().getId(),
                analise.getEnsaio().getNome()
        );

        LoteDTO loteDTO = new LoteDTO(
                analise.getLote().getId(),
                analise.getLote().getAmostra(),
                analise.getLote().getNotaFiscal(),
                analise.getLote().getDataEntrada(),
                analise.getLote().getDataValidade(),
                analise.getLote().getDescricao(),
                analise.getLote().getQuantidade(),
                null
        );

        AnaliseDTO dto = new AnaliseDTO(
                analise.getId(),
                analise.getEspecificacao(),
                analise.getResultado(),
                analise.getUnidade(),
                analise.getObservacao(),
                loteDTO,
                ensaioDTO
        );

        return dto;
    }
}
