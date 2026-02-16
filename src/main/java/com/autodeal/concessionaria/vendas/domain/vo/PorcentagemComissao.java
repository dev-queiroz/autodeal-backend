package com.autodeal.concessionaria.vendas.domain.vo;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Getter
public final class PorcentagemComissao {

    private final BigDecimal valor;

    private PorcentagemComissao(BigDecimal valor) {
        this.valor = valor.setScale(2, RoundingMode.HALF_EVEN);
    }

    public static PorcentagemComissao of(BigDecimal porcentagem) {
        if (porcentagem == null || porcentagem.compareTo(BigDecimal.ZERO) < 0 || porcentagem.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("Porcentagem de comissão deve estar entre 0 e 100");
        }
        return new PorcentagemComissao(porcentagem);
    }

    public BigDecimal aplicarSobre(BigDecimal valorBase) {
        return valorBase.multiply(valor.divide(new BigDecimal("100")));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PorcentagemComissao that = (PorcentagemComissao) o;
        return valor.compareTo(that.valor) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor.stripTrailingZeros());
    }

    @Override
    public String toString() {
        return valor + "%";
    }
}