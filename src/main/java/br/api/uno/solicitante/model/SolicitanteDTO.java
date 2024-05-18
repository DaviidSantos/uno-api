package br.api.uno.solicitante.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record SolicitanteDTO(
        @NotNull(message = "Campo CNPJ é obrigatório!")
        @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}", message = "Formato do CNPJ inválido! Formato: XX.XXX.XXX/XXXX-XX")
        String cnpj,

        @NotNull(message = "Campo nome é obrigatório!")
        String nome,

        @NotNull(message = "Campo telefone é obrigatório!")
        @Pattern(regexp = "^\\(\\d{2}\\) \\d{5}-\\d{4}$", message = "Formato de telefone inválido! Formato: (XX) XXXXX-XXXX")
        String telefone,

        @NotNull(message = "Campo email é obrigatório!")
        @Email(message = "Formato de email inválido!")
        String email,

        @NotNull(message = "Campo endereco é obrigatório!")
        String endereco,

        @NotNull(message = "Campo cidade é obrigatório!")
        String cidade,

        @NotNull(message = "Campo estado é obrigatório!")
        String estado
) {
}
