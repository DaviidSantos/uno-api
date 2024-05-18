package br.api.uno.solicitacaoAnalise.repository;

import br.api.uno.solicitacaoAnalise.model.SolicitacaoAnalise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SolicitacaoAnaliseRepository extends JpaRepository<SolicitacaoAnalise, UUID> {
    Optional<SolicitacaoAnalise> findByIdSa(String idSa);

    int countByIdSaEndingWith(int anoAtual);
}
