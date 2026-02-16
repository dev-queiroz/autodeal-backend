package com.autodeal.concessionaria.estoque.dto;

import com.autodeal.concessionaria.estoque.domain.enums.EstadoVeiculo;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record VeiculoRequest(
        @NotBlank String chassi,
        @NotBlank String placa,
        @NotBlank String marca,
        @NotBlank String modelo,
        @Min(1900) Integer anoFabricacao,
        @Min(1900) Integer anoModelo,
        @NotBlank String cor,
        @Min(0) Integer quilometragem,
        @NotNull EstadoVeiculo estadoInicial,
        @NotNull @DecimalMin("0.0") BigDecimal precoCompra,
        @NotNull @DecimalMin("0.0") BigDecimal precoVendaSugerido,
        @NotNull Long fornecedorId
) {}