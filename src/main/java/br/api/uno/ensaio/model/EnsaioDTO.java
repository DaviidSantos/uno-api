package br.api.uno.ensaio.model;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EnsaioDTO(
        UUID id,

        @NotNull(message = "Campo nome é obrigatório!")
        String nome
) {}
