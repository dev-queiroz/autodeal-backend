package com.autodeal.concessionaria.vendas.dto;

import java.math.BigDecimal;

public record CalculoPrecoResponse(
        BigDecimal precoBase,
        BigDecimal descontoAplicado,
        BigDecimal impostos,
        BigDecimal taxaAdministrativa,
        BigDecimal precoFinal
) {}