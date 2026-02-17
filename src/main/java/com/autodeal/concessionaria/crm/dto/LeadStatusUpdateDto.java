package com.autodeal.concessionaria.crm.dto;

import jakarta.validation.constraints.NotBlank;

public record LeadStatusUpdateDto(
        @NotBlank String novoStatus
) {}