package br.api.uno.reagenteAnalise.model;

import br.api.uno.reagenteAnalise.model.pk.ReagenteAnalisePK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;

@Entity(name = "reagente_analise")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReagenteAnalise implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ReagenteAnalisePK id = new ReagenteAnalisePK();

    private Double quantidade;
}
