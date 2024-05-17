package br.api.uno.reagenteAnalise.repository;

import br.api.uno.reagenteAnalise.model.ReagenteAnalise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReagenteAnaliseRepository extends JpaRepository<ReagenteAnalise, UUID> {
}
