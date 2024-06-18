package br.api.uno.solicitante.model;

import br.api.uno.estoque.model.Estoque;
import br.api.uno.solicitacaoAnalise.model.SolicitacaoAnalise;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "solicitante")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Solicitante implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String cnpj;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    @JsonIgnore
    @OneToMany(mappedBy = "solicitante")
    private Set<Estoque> estoques = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "solicitante")
    private Set<SolicitacaoAnalise> solicitacoesAnalise = new HashSet<>();

    public Solicitante(String cnpj, String nome, String telefone, String email, String endereco, String cidade, String estado) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.cidade = cidade;
        this.estado = estado;
    }
}
