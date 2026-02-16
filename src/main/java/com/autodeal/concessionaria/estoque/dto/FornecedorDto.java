package com.autodeal.concessionaria.estoque.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FornecedorDto(
        Long id,

        @NotBlank
        @Size(max = 255)
        String nome,

        @NotBlank
        @Size(max = 18)
        String cnpj,

        @NotBlank
        @Email
        @Size(max = 255)
        String email,

        @Size(max = 20)
        String telefone,

        @Size(max = 100)
        String contatoResponsavel
) {}
