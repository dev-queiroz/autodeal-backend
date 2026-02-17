package com.autodeal.concessionaria.vendas.application;

import com.autodeal.concessionaria.estoque.domain.Veiculo;
import com.autodeal.concessionaria.shared.vo.Dinheiro;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CalculadoraPrecoVenda {

    private static final BigDecimal MARGEM_MINIMA = new BigDecimal("0.15"); // 15%
    private static final BigDecimal TAXA_ADMIN = new BigDecimal("500.00");
    private static final BigDecimal IMPOSTOS = new BigDecimal("0.12"); // 12% exemplo

    public Dinheiro calcularPrecoFinal(Veiculo veiculo, BigDecimal descontoPct) {
        Dinheiro base = veiculo.getPrecoVendaSugerido();

        BigDecimal desconto = BigDecimal.ZERO;
        if (descontoPct != null && descontoPct.compareTo(BigDecimal.ZERO) > 0) {
            desconto = base.getValue().multiply(descontoPct.divide(BigDecimal.valueOf(100)));
        }

        BigDecimal comImpostos = base.getValue().add(base.getValue().multiply(IMPOSTOS)).add(TAXA_ADMIN);
        Dinheiro precoFinal = new Dinheiro(comImpostos.subtract(desconto));

        if (!validarMargemMinima(veiculo, precoFinal)) {
            throw new IllegalArgumentException("Margem abaixo do mínimo permitido");
        }

        return precoFinal;
    }

    public Dinheiro calcularComissao(Dinheiro valorVenda, BigDecimal porcentagem) {
        return Dinheiro.of(valorVenda.getValue().multiply(porcentagem.divide(BigDecimal.valueOf(100))));
    }

    private boolean validarMargemMinima(Veiculo veiculo, Dinheiro precoFinal) {
        BigDecimal margem = precoFinal.getValue().subtract(veiculo.getPrecoCompra().getValue());
        BigDecimal pct = margem.divide(veiculo.getPrecoCompra().getValue(), 4, BigDecimal.ROUND_HALF_UP);
        return pct.compareTo(MARGEM_MINIMA) >= 0;
    }
}