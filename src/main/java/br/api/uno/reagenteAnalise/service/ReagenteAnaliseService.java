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
        Reagente reagente = reagenteService.buscarReagentePorId(UUID.fromString(dto.reagente_id()));
        Analise analise = analiseService.buscarAnalisePorId(UUID.fromString(dto.analise_id()));

        ReagenteAnalisePK pk = new ReagenteAnalisePK();
        pk.setReagente(reagente);
        pk.setAnalise(analise);

        reagenteService.retirarReagente(reagente.getId(), dto.quantidade());

        ReagenteAnalise reagenteAnalise = new ReagenteAnalise(pk, dto.quantidade());
        repository.save(reagenteAnalise);
    }

    public List<ReagenteAnaliseDTO> listarReagentesPorAnalise(UUID analiseId) {
        List<ReagenteAnalise> reagentes = repository.findAll().stream().filter(reagenteAnalise -> reagenteAnalise.getId().getAnalise().getId() == analiseId).toList();
        List<ReagenteAnaliseDTO> dtos = new ArrayList<>();

        for (ReagenteAnalise reagenteAnalise : reagentes) {
            ReagenteAnaliseDTO dto = new ReagenteAnaliseDTO(
                    reagenteAnalise.getId().getReagente().getId().toString(),
                    reagenteAnalise.getId().getAnalise().getId().toString(),
                    reagenteAnalise.getQuantidade()
            );

            dtos.add(dto);
        }

        return dtos;
    }

}
