package br.api.uno.analise.model;

import br.api.uno.ensaio.model.EnsaioDTO;
import br.api.uno.lote.model.LoteDTO;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AnaliseDTO (
        UUID id,

        @NotNull(message = "Campo Especificação é obrigatório!")
        Double especificacao,

        Double resultado,

        @NotNull(message = "Campo Unidade é obrigatório!")
        String unidade,

        String observacao,

        @NotNull(message = "Campo Lote é obrigatório!")
        LoteDTO lote,

        @NotNull(message = "Campo Ensaio é obrigatório!")
        EnsaioDTO ensaio
) {
}
