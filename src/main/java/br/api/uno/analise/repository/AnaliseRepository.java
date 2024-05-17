package br.api.uno.analise.repository;

import br.api.uno.analise.model.Analise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnaliseRepository extends JpaRepository<Analise, UUID> {
}
