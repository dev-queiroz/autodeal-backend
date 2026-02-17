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

    @Column(nullable = false)
    private BigDecimal valorFinanciado;

    @Column(nullable = false)
    private BigDecimal taxaJurosMensal;

    @Column(nullable = false)
    private int quantidadeParcelas;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @ElementCollection
    @CollectionTable(name = "financiamento_parcelas", joinColumns = @JoinColumn(name = "financiamento_id"))
    private List<Parcela> parcelas;

    public Financiamento(Venda venda, BigDecimal valorFinanciado, BigDecimal taxaJurosMensal, int quantidadeParcelas) {
        this.venda = venda;
        this.valorFinanciado = valorFinanciado;
        this.taxaJurosMensal = taxaJurosMensal;
        this.quantidadeParcelas = quantidadeParcelas;
        this.dataInicio = LocalDate.now();
        // Aqui você pode calcular as parcelas no service ou no construtor
    }
}