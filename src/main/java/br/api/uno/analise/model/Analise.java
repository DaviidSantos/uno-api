package br.api.uno.analise.model;

import br.api.uno.ensaio.model.Ensaio;
import br.api.uno.lote.model.Lote;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity(name = "analise")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Analise implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Double especificacao;

    private Double resultado;

    @Column(nullable = false)
    private String unidade;

    private String observacao;

    @ManyToOne
    @JoinColumn(name = "lote")
    private Lote lote;

    @ManyToOne
    @JoinColumn(name = "ensaio")
    private Ensaio ensaio;

}
