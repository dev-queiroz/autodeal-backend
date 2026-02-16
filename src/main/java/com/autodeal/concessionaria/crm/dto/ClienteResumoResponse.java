package com.autodeal.concessionaria.crm.dto;

public record ClienteResumoResponse(
        Long id,
        String nomeCompleto,
        String cpf,
        String email
) {}