package br.api.uno.analise.repository;

import br.api.uno.analise.model.Analise;
import br.api.uno.lote.model.Lote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AnaliseRepository extends JpaRepository<Analise, UUID> {
    List<Analise> findAllByLote(Lote lote);
}
