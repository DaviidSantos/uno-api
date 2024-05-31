package br.api.uno.solicitacaoAnalise.repository;

import br.api.uno.solicitacaoAnalise.model.SolicitacaoAnalise;
import br.api.uno.solicitante.model.Solicitante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SolicitacaoAnaliseRepository extends JpaRepository<SolicitacaoAnalise, UUID> {
    Optional<SolicitacaoAnalise> findByIdSa(String idSa);

    List<SolicitacaoAnalise> findAllBySolicitante(Solicitante solicitante);

    int countByIdSaEndingWith(int anoAtual);
}
