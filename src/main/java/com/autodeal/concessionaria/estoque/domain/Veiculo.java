package com.autodeal.concessionaria.estoque.domain;

import com.autodeal.concessionaria.crm.domain.Cliente;
import com.autodeal.concessionaria.estoque.domain.enums.EstadoVeiculo;
import com.autodeal.concessionaria.shared.vo.Dinheiro;
import com.autodeal.concessionaria.shared.vo.PlacaVeicular;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "veiculos")
@Getter
@NoArgsConstructor
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String chassi;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "placa"))
    })
    private PlacaVeicular placa;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private Integer anoFabricacao;

    @Column(nullable = false)
    private Integer anoModelo;

    @Column(nullable = false)
    private String cor;

    @Column(nullable = false)
    private Integer quilometragem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoVeiculo estado;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "preco_compra"))
    })
    private Dinheiro precoCompra;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "preco_venda_sugerido"))
    })
    private Dinheiro precoVendaSugerido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "data_entrada_estoque")
    private LocalDate dataEntradaEstoque = LocalDate.now();

    private boolean ativo = true;

    public Veiculo(
            String chassi,
            PlacaVeicular placa,
            String marca,
            String modelo,
            Integer anoFabricacao,
            Integer anoModelo,
            String cor,
            Integer quilometragem,
            EstadoVeiculo estado,
            Dinheiro precoCompra,
            Dinheiro precoVendaSugerido,
            Fornecedor fornecedor
    ) {
        this.chassi = chassi;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anoFabricacao = anoFabricacao;
        this.anoModelo = anoModelo;
        this.cor = cor;
        this.quilometragem = quilometragem;
        this.estado = estado;
        this.precoCompra = precoCompra;
        this.precoVendaSugerido = precoVendaSugerido;
        this.fornecedor = fornecedor;
    }

    public void atualizarEstado(EstadoVeiculo novoEstado) {
        if (novoEstado == EstadoVeiculo.VENDIDO && this.estado.isDisponivelParaVenda()) {
            this.estado = novoEstado;
        } else if (novoEstado != EstadoVeiculo.VENDIDO) {
            this.estado = novoEstado;
        } else {
            throw new IllegalStateException("Não é possível marcar como vendido um veículo já vendido ou bloqueado");
        }
    }
    
    public void desativar() {
        this.ativo = false;
    }
}
