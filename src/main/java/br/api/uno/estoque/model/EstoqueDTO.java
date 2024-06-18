package br.api.uno.estoque.model;

import br.api.uno.solicitante.model.SolicitanteDTO;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EstoqueDTO(
        UUID id,

        @NotNull(message = "Campo nome é obrigatório!")
        String nome,
        SolicitanteDTO solicitante
) {}
