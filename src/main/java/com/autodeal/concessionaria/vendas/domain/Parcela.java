package com.autodeal.concessionaria.vendas.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Embeddable
@Getter
@NoArgsConstructor
public class Parcela {

    @Column(nullable = false)
    private int numero;

    @Column(nullable = false)
    private BigDecimal valorParcela;

    @Column(nullable = false)
    private LocalDate dataVencimento;

    @Column(nullable = false)
    private boolean paga = false;

    public Parcela(int numero, BigDecimal valorParcela, LocalDate dataVencimento) {
        this.numero = numero;
        this.valorParcela = valorParcela;
        this.dataVencimento = dataVencimento;
    }

    public void marcarComoPaga() {
        this.paga = true;
    }
}