package com.autodeal.concessionaria.estoque.dto;

import com.autodeal.concessionaria.estoque.domain.enums.EstadoVeiculo;

import java.math.BigDecimal;
import java.time.LocalDate;

public record VeiculoResponse(
        Long id,
        String chassi,
        String placa,
        String marca,
        String modelo,
        Integer anoFabricacao,
        Integer anoModelo,
        String cor,
        Integer quilometragem,
        EstadoVeiculo estado,
        BigDecimal precoCompra,
        BigDecimal precoVendaSugerido,
        Long fornecedorId,
        String fornecedorNome,
        LocalDate dataEntradaEstoque,
        boolean ativo
) {}