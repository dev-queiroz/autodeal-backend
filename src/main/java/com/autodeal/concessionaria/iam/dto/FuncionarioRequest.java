package com.autodeal.concessionaria.iam.dto;

import com.autodeal.concessionaria.iam.domain.enums.PerfilAcesso;
import jakarta.validation.constraints.*;

public record FuncionarioRequest(
        @NotBlank String nomeCompleto,
        @NotBlank @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11}") String cpf,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6) String senha,
        @NotNull PerfilAcesso perfilAcesso
) {}