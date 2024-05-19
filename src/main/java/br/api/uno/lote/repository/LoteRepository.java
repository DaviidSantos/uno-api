package br.api.uno.lote.repository;

import br.api.uno.lote.model.Lote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoteRepository extends JpaRepository<Lote, UUID> {

    boolean existsByNotaFiscal(String notaFiscal);
}
