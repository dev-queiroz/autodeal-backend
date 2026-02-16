package com.autodeal.concessionaria.vendas.dto;

import java.math.BigDecimal;

public record VendaResumoResponse(
        Long id,
        String placaVeiculo,
        String nomeCliente,
        String nomeVendedor,
        BigDecimal valorVenda,
        String status
) {}