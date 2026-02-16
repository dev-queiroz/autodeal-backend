package com.autodeal.concessionaria.estoque.dto;

import com.autodeal.concessionaria.estoque.domain.enums.EstadoVeiculo;

import java.math.BigDecimal;

public record VeiculoResumoResponse(
        Long id,
        String placa,
        String marca,
        String modelo,
        Integer anoModelo,
        EstadoVeiculo estado,
        BigDecimal precoVendaSugerido,
        boolean ativo
) {}