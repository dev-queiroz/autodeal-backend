package com.autodeal.concessionaria.vendas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ParcelaFinanciamentoDto(
        int numeroParcela,
        BigDecimal valorParcela,
        LocalDate dataVencimento
) {}