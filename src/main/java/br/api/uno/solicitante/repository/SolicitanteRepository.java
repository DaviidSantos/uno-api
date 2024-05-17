package br.api.uno.solicitante.repository;

import br.api.uno.solicitante.model.Solicitante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SolicitanteRepository extends JpaRepository<Solicitante, UUID> {
    boolean existsByCnpj(String cnpj);

    Optional<Solicitante> findByCnpj(String cnpj);
}
