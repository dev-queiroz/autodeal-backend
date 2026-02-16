package com.autodeal.concessionaria.vendas.application;

import com.autodeal.concessionaria.estoque.domain.Veiculo;
import com.autodeal.concessionaria.shared.vo.Dinheiro;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CalculadoraPrecoVenda {

    private static final BigDecimal MARGEM_MINIMA = new BigDecimal("0.15"); // 15% mínimo sobre compra
    private static final BigDecimal TAXA_ADMINISTRATIVA = new BigDecimal("500.00");
    private static final BigDecimal PERCENTUAL_IMPOSTOS = new BigDecimal("0.12"); // exemplo 12%

    public Dinheiro calcularPrecoVendaFinal(Veiculo veiculo, BigDecimal descontoPercentual) {
        Dinheiro precoBase = veiculo.getPrecoVendaSugerido();

        // Aplica desconto se houver
        if (descontoPercentual != null && descontoPercentual.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal desconto = precoBase.getValue().multiply(descontoPercentual.divide(new BigDecimal("100")));
            precoBase = precoBase.subtrair(Dinheiro.of(desconto));
        }

        // Adiciona impostos e taxa administrativa
        BigDecimal impostos = precoBase.getValue().multiply(PERCENTUAL_IMPOSTOS);
        BigDecimal precoComImpostos = precoBase.getValue().add(impostos).add(TAXA_ADMINISTRATIVA);

        return Dinheiro.of(precoComImpostos);
    }

    public Dinheiro calcularComissaoVendedor(Dinheiro valorVenda, BigDecimal porcentagemComissao) {
        return Dinheiro.of(valorVenda.getValue().multiply(porcentagemComissao.divide(new BigDecimal("100"))));
    }

    public boolean validarMargemMinima(Veiculo veiculo, Dinheiro precoVendaFinal) {
        BigDecimal margem = precoVendaFinal.getValue().subtract(veiculo.getPrecoCompra().getValue());
        BigDecimal margemPercentual = margem.divide(veiculo.getPrecoCompra().getValue(), 4, BigDecimal.ROUND_HALF_UP);
        return margemPercentual.compareTo(MARGEM_MINIMA) >= 0;
    }
}