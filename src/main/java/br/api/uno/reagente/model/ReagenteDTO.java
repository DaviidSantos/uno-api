package br.api.uno.reagente.model;

import br.api.uno.estoque.model.EstoqueDTO;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReagenteDTO(
        UUID id,

        @NotNull(message = "Campo Nome é obrigatório!")
        String nome,

        @NotNull(message = "Campo Fornecedor é obrigatório!")
        String fornecedor,

        String descricao,

        @NotNull(message = "Campo Unidade é obrigatório!")
        String unidade,

        @NotNull(message = "Campo Quantidade é obrigatório!")
        Double quantidade,

        @NotNull(message = "Campo Estoque é obrigatório!")
        EstoqueDTO estoque
){}
