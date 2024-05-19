package br.api.uno.reagenteAnalise.service;

import br.api.uno.analise.model.Analise;
import br.api.uno.analise.model.AnaliseDTO;
import br.api.uno.analise.service.AnaliseService;
import br.api.uno.reagente.model.Reagente;
import br.api.uno.reagente.model.ReagenteDTO;
import br.api.uno.reagente.service.ReagenteService;
import br.api.uno.reagenteAnalise.model.ReagenteAnalise;
import br.api.uno.reagenteAnalise.model.ReagenteAnaliseDTO;
import br.api.uno.reagenteAnalise.model.pk.ReagenteAnalisePK;
import br.api.uno.reagenteAnalise.repository.ReagenteAnaliseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReagenteAnaliseService {
    private final ReagenteAnaliseRepository repository;

    private final ReagenteService reagenteService;

    private final AnaliseService analiseService;

    public ReagenteAnaliseService(ReagenteAnaliseRepository repository, ReagenteService reagenteService, AnaliseService analiseService) {
        this.repository = repository;
        this.reagenteService = reagenteService;
        this.analiseService = analiseService;
    }

    public void cadastrarReagenteUsado(ReagenteAnaliseDTO dto) {
        Reagente reagente = reagenteService.buscarReagentePorId(dto.reagente().id());
        Analise analise = analiseService.buscarAnalisePorId(dto.analise().id());

        ReagenteAnalisePK pk = new ReagenteAnalisePK();
        pk.setReagente(reagente);
        pk.setAnalise(analise);

        reagenteService.retirarReagente(reagente.getId(), dto.quantidade());

        ReagenteAnalise reagenteAnalise = new ReagenteAnalise(pk, dto.quantidade());
        repository.save(reagenteAnalise);
    }

    public List<ReagenteAnaliseDTO> listarReagentesPorAnalise(UUID analiseId) {
        Analise analise = analiseService.buscarAnalisePorId(analiseId);
        List<ReagenteAnalise> reagentes = repository.findAll().stream().filter(reagenteAnalise -> reagenteAnalise.getId().getAnalise().getId() == analiseId).toList();
        List<ReagenteAnaliseDTO> dtos = new ArrayList<>();

        for (ReagenteAnalise reagenteAnalise : reagentes) {
            ReagenteAnaliseDTO dto = entityToDTO(reagenteAnalise);

            dtos.add(dto);
        }

        return dtos;
    }

    private static ReagenteAnaliseDTO entityToDTO(ReagenteAnalise reagenteAnalise) {
        ReagenteDTO reagenteDTO = new ReagenteDTO(
                reagenteAnalise.getId().getReagente().getId(),
                reagenteAnalise.getId().getReagente().getNome(),
                reagenteAnalise.getId().getReagente().getFornecedor(),
                reagenteAnalise.getId().getReagente().getDescricao(),
                reagenteAnalise.getId().getReagente().getUnidade(),
                reagenteAnalise.getId().getReagente().getQuantidade(),
                null
        );

        AnaliseDTO analiseDTO = new AnaliseDTO(
                reagenteAnalise.getId().getAnalise().getId(),
                reagenteAnalise.getId().getAnalise().getEspecificacao(),
                reagenteAnalise.getId().getAnalise().getResultado(),
                reagenteAnalise.getId().getAnalise().getUnidade(),
                reagenteAnalise.getId().getAnalise().getObservacao(),
                null,
                null
        );

        ReagenteAnaliseDTO dto = new ReagenteAnaliseDTO(
                reagenteDTO,
                analiseDTO,
                reagenteAnalise.getQuantidade()
        );
        return dto;
    }
}
