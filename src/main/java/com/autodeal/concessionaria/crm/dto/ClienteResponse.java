package com.autodeal.concessionaria.crm.dto;

import java.time.LocalDateTime;

public record ClienteResponse(
        Long id,
        String nomeCompleto,
        String cpf,
        String email,
        String telefone,
        boolean ativo,
        LocalDateTime dataCadastro
) {
}
