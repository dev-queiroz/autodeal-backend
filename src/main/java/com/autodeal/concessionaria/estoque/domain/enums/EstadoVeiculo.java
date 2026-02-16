package com.autodeal.concessionaria.estoque.domain.enums;

import lombok.Getter;

@Getter
public enum EstadoVeiculo {

    NOVO("Novo", "Veículo 0 km, direto da montadora"),
    SEMINOVO("Seminovo", "Pouco uso, geralmente até 3 anos ou 60.000 km"),
    USADO("Usado", "Veículo com mais uso, acima de 3 anos ou 60.000 km"),
    DEMONSTRACAO("Demonstração", "Veículo usado pela concessionária para test-drive"),
    CONSIGNADO("Consignado", "Veículo de terceiro em consignação"),
    BLOQUEADO("Bloqueado", "Veículo com pendência ou bloqueado para venda"),
    VENDIDO("Vendido", "Veículo já vendido, aguardando transferência");

    private final String descricao;
    private final String detalhe;

    EstadoVeiculo(String descricao, String detalhe) {
        this.descricao = descricao;
        this.detalhe = detalhe;
    }

    public boolean isDisponivelParaVenda() {
        return this == NOVO || this == SEMINOVO || this == USADO || this == DEMONSTRACAO || this == CONSIGNADO;
    }
}