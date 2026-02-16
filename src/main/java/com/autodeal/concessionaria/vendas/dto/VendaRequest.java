package com.autodeal.concessionaria.vendas.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record VendaRequest(
        @NotNull Long veiculoId,
        @NotNull Long clienteId,
        @NotNull Long vendedorId,
        @PositiveOrZero BigDecimal descontoPercentual
) {}