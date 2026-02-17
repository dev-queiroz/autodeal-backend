package com.autodeal.concessionaria.crm.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LeadRequest(
        @NotBlank String nome,
        @NotBlank @Email String email,
        String telefone,
        @NotBlank String origem
) {}