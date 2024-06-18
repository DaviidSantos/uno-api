package br.api.uno.estoque.model;

import br.api.uno.reagente.model.Reagente;
import br.api.uno.solicitante.model.Solicitante;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "estoque")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Estoque implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "solicitante")
    private Solicitante solicitante;

    @JsonIgnore
    @OneToMany(mappedBy = "estoque")
    private Set<Reagente> reagentes = new HashSet<>();
}
