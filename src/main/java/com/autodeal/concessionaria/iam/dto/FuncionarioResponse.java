package com.autodeal.concessionaria.iam.dto;

import com.autodeal.concessionaria.iam.domain.enums.PerfilAcesso;

import java.time.LocalDateTime;

public record FuncionarioResponse(
        Long id,
        String nomeCompleto,
        String cpf,
        String email,
        PerfilAcesso perfilAcesso,
        boolean ativo,
        LocalDateTime dataCadastro
) {}