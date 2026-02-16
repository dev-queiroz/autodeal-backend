package com.autodeal.concessionaria.iam.dto;

import com.autodeal.concessionaria.iam.domain.enums.PerfilAcesso;

public record FuncionarioResumoResponse(
        Long id,
        String nomeCompleto,
        String email,
        PerfilAcesso perfilAcesso,
        boolean ativo
) {}