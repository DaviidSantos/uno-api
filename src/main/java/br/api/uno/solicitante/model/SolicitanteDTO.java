package br.api.uno.solicitante.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record SolicitanteDTO(
        @NotNull(message = "Campo CNPJ é obrigatório!")
        @Pattern(regexp = "^[0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2}$", message = "Formato do CNPJ inválido! Formato: XX.XXX.XXX/XXXX-XX")
        String cnpj,

        @NotNull(message = "Campo nome é obrigatório!")
        String nome,

        @NotNull(message = "Campo telefone é obrigatório!")
        @Pattern(regexp = "^\\([1-9]{2}\\) (?:[2-8]|9[0-9])[0-9]{3}\\-[0-9]{4}$", message = "Formato de telefone inválido! Formato: (XX) XXXXX-XXXX")
        String telefone,

        @NotNull(message = "Campo email é obrigatório!")
        @Pattern(regexp = "/^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$/gm", message = "Formato email inválido!")
        String email,

        @NotNull(message = "Campo endereco é obrigatório!")
        String endereco,

        @NotNull(message = "Campo cidade é obrigatório!")
        String cidade,

        @NotNull(message = "Campo estado é obrigatório!")
        String estado
) {
}
