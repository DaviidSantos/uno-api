package br.api.uno.reagenteAnalise.model.pk;

import br.api.uno.analise.model.Analise;
import br.api.uno.reagente.model.Reagente;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
@Setter
public class ReagenteAnalisePK implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "reagente_id")
    private Reagente reagente;

    @ManyToOne
    @JoinColumn(name = "analise_id")
    private Analise analise;
}
