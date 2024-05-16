package br.api.uno.reagente.model;

import br.api.uno.estoque.model.Estoque;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity(name = "reagente")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reagente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    private String fornecedor;

    private String descricao;

    private Double quantidade;

    private String unidade;

    @ManyToOne
    @JoinColumn(name = "estoque")
    private Estoque estoque;

    public void retirarReagente(double quantidade) {
        this.quantidade -= quantidade;
    }
}
