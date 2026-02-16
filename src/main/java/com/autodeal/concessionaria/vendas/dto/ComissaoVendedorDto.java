package com.autodeal.concessionaria.vendas.dto;

import java.math.BigDecimal;

public record ComissaoVendedorDto(
        Long vendedorId,
        String nomeVendedor,
        BigDecimal valorComissao,
        Long vendaId
) {}