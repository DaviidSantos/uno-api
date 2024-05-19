package br.api.uno.reagente.repository;

import br.api.uno.estoque.model.Estoque;
import br.api.uno.reagente.model.Reagente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReagenteRepository extends JpaRepository<Reagente, UUID> {
    List<Reagente> findAllByEstoque(Estoque estoque);
}
