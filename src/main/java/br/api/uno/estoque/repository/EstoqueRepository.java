package br.api.uno.estoque.repository;

import br.api.uno.estoque.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EstoqueRepository extends JpaRepository<Estoque, UUID> {
    boolean existsByNome(String nome);

    Optional<Estoque> findByNome(String nome);
}
