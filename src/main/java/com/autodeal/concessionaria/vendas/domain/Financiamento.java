package com.autodeal.concessionaria.vendas.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "financiamentos")
@Getter
@NoArgsConstructor
public class Financiamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "venda_id")
    private Venda venda;

    private BigDecimal valorFinanciado;
    private BigDecimal taxaJurosMensal;
    private int quantidadeParcelas;
    private LocalDate dataInicio;

    @ElementCollection
    private List<Parcela> parcelas;

    public Financiamento(Venda venda, BigDecimal valorFinanciado, BigDecimal taxaJurosMensal, int parcelas) {
        this.venda = venda;
        this.valorFinanciado = valorFinanciado;
        this.taxaJurosMensal = taxaJurosMensal;
        this.quantidadeParcelas = parcelas;
        this.dataInicio = LocalDate.now();
        // Calcular parcelas aqui ou em service
    }
}