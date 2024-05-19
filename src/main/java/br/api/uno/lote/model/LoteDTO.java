package br.api.uno.lote.model;

import br.api.uno.solicitacaoAnalise.model.SolicitacaoAnaliseDTO;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record LoteDTO(
        UUID id,

        @NotNull(message = "Campo Amostra é obrigatório!")
        String amostra,

        @NotNull(message = "Campo Nota Fiscal é obrigatório!")
        String notaFiscal,

        LocalDate dataEntrada,

        @NotNull(message = "Campo Data de Validade é obrigatório!")
        LocalDate dataValidade,

        String descricao,

        @NotNull(message = "Campo Quantidade é obrigatório!")
        Integer quantidade,

        @NotNull(message = "Campo Solicitação de Análise é obrigatório!")
        SolicitacaoAnaliseDTO solicitacaoAnalise
) {
}
