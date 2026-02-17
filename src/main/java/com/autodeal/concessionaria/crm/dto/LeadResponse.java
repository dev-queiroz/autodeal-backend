package com.autodeal.concessionaria.crm.dto;

import java.time.LocalDateTime;

public record LeadResponse(
        Long id,
        String nome,
        String email,
        String telefone,
        String origem,
        String status,
        LocalDateTime dataCriacao
) {}