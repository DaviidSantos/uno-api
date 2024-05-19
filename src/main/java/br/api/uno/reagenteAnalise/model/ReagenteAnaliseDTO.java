package br.api.uno.reagenteAnalise.model;

import br.api.uno.analise.model.AnaliseDTO;
import br.api.uno.reagente.model.ReagenteDTO;
import jakarta.validation.constraints.NotNull;

public record ReagenteAnaliseDTO(
        @NotNull(message = "Campo Reagente é obrigatório!")
        ReagenteDTO reagente,

        @NotNull(message = "Campo Analise é obrigatório!")
        AnaliseDTO analise,

        @NotNull(message = "Campo Quantidade Utilizada é obrigatório!")
        Double quantidade
) {
}
