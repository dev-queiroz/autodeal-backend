package com.autodeal.concessionaria.shared.vo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Dinheiro {

    private final BigDecimal value;

    private Dinheiro(BigDecimal value) {
        this.value = value.setScale(2, RoundingMode.HALF_EVEN);
    }

    public static Dinheiro of(BigDecimal valor) {
        if (valor == null) {
            throw new IllegalArgumentException("Valor não pode ser nulo");
        }
        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Valor monetário não pode ser negativo");
        }
        return new Dinheiro(valor);
    }

    public static Dinheiro of(double valor) {
        return of(BigDecimal.valueOf(valor));
    }

    public static Dinheiro zero() {
        return of(BigDecimal.ZERO);
    }

    public BigDecimal getValue() {
        return value;
    }

    public Dinheiro somar(Dinheiro outro) {
        return new Dinheiro(this.value.add(outro.value));
    }

    public Dinheiro subtrair(Dinheiro outro) {
        BigDecimal resultado = this.value.subtract(outro.value);
        if (resultado.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Resultado não pode ser negativo");
        }
        return new Dinheiro(resultado);
    }

    public Dinheiro multiplicarPor(double fator) {
        if (fator < 0) {
            throw new IllegalArgumentException("Fator não pode ser negativo");
        }
        return new Dinheiro(this.value.multiply(BigDecimal.valueOf(fator)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dinheiro dinheiro = (Dinheiro) o;
        return value.compareTo(dinheiro.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value.stripTrailingZeros());
    }

    @Override
    public String toString() {
        return "R$ " + value.toPlainString();
    }
}