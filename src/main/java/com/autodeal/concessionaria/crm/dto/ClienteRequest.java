package com.autodeal.concessionaria.crm.dto;

import jakarta.validation.constraints.*;

public record ClienteRequest(
        @NotBlank String nomeCompleto,
        @NotBlank @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11}") String cpf,
        @NotBlank @Email String email,
        @NotBlank String telefone
) {}