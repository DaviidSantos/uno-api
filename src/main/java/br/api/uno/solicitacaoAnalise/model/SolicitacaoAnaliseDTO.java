package br.api.uno.solicitacaoAnalise.model;

import br.api.uno.solicitante.model.SolicitanteDTO;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record SolicitacaoAnaliseDTO(
        UUID id,

        String idSa,

        @NotNull(message = "Campo Nome do Projeto é obrigatório!")
        String nomeProjeto,

        @NotNull(message = "Campo Tipo de Análise é obrigatório!")
        String tipoAnalise,

        @NotNull(message = "Campo Prazo Acordado é obrigatório!")
        LocalDate prazoAcordado,

        LocalDate conclusaoProjeto,

        @NotNull(message = "Campo Descrição do Projeto é obrigatório!")
        String descricaoProjeto,

        @NotNull(message = "Campo Solicitante é obrigatório!")
        SolicitanteDTO solicitante
) {
}
