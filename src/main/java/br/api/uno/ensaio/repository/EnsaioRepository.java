package br.api.uno.ensaio.repository;

import br.api.uno.ensaio.model.Ensaio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EnsaioRepository extends JpaRepository<Ensaio, UUID> {
    Optional<Ensaio> findByNome(String nome);
}
