package br.api.uno.lote.model;

import br.api.uno.analise.model.Analise;
import br.api.uno.solicitacaoAnalise.model.SolicitacaoAnalise;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "lote")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lote implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String amostra;

    @Column(nullable = false)
    private String notaFiscal;

    @Column(nullable = false)
    private LocalDate dataEntrada;

    @Column(nullable = false)
    private LocalDate dataValidade;

    private String descricao;

    @Column(nullable = false)
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "solicitacao_analise")
    private SolicitacaoAnalise solicitacaoAnalise;

    @JsonIgnore
    @OneToMany(mappedBy = "lote")
    private Set<Analise> analises = new HashSet<>();

    public void retirarAmostra(int quantidade) {
        this.quantidade -= quantidade;
    }
}
