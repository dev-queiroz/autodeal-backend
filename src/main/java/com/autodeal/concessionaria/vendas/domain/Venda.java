package com.autodeal.concessionaria.vendas.domain;

import com.autodeal.concessionaria.estoque.domain.Veiculo;
import com.autodeal.concessionaria.crm.domain.Cliente;
import com.autodeal.concessionaria.iam.domain.Funcionario;
import com.autodeal.concessionaria.shared.vo.Dinheiro;
import com.autodeal.concessionaria.vendas.domain.vo.PorcentagemComissao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "vendas")
@Getter
@NoArgsConstructor
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Funcionario vendedor;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "valor_venda"))
    })
    private Dinheiro valorVenda;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "comissao_vendedor"))
    })
    private Dinheiro comissaoVendedor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusVenda status = StatusVenda.PENDENTE;

    @Column(name = "data_venda")
    private LocalDateTime dataVenda = LocalDateTime.now();

    public Venda(Veiculo veiculo, Cliente cliente, Funcionario vendedor, Dinheiro valorVenda, PorcentagemComissao porcentagemComissao) {
        this.veiculo = veiculo;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.valorVenda = valorVenda;
        this.comissaoVendedor = new Dinheiro(porcentagemComissao.aplicarSobre(valorVenda.getValue()));
    }

    public enum StatusVenda {
        PENDENTE, CONCLUIDA, CANCELADA
    }
}