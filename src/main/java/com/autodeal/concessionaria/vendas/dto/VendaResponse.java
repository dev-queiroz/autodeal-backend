package com.autodeal.concessionaria.vendas.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record VendaResponse(
        Long id,
        Long veiculoId,
        String placaVeiculo,
        Long clienteId,
        String nomeCliente,
        Long vendedorId,
        String nomeVendedor,
        BigDecimal valorVenda,
        BigDecimal comissaoVendedor,
        String status,
        LocalDateTime dataVenda
) {}