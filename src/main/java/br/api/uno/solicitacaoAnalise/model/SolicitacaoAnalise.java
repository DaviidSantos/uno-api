package br.api.uno.solicitacaoAnalise.model;

import br.api.uno.lote.model.Lote;
import br.api.uno.solicitacaoAnalise.model.enums.TipoAnalise;
import br.api.uno.solicitante.model.Solicitante;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "solicitacao_analise")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolicitacaoAnalise implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String idSa;

    private TipoAnalise tipoAnalise;

    @Column(nullable = false)
    private LocalDate prazoAcordado;

    private LocalDate conclusaoProjeto;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricaoProjeto;

    @ManyToOne
    @JoinColumn(name = "solicitante")
    private Solicitante solicitante;

    @JsonIgnore
    @OneToMany(mappedBy = "solicitacaoAnalise")
    private Set<Lote> lotes = new HashSet<>();
}
