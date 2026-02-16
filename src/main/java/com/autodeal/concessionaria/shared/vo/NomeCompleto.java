package com.autodeal.concessionaria.shared.vo;

import java.util.Objects;

public final class NomeCompleto {

    private final String value;

    private NomeCompleto(String value) {
        this.value = value;
    }

    public static NomeCompleto of(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome completo não pode ser nulo ou vazio");
        }
        String trimmed = nome.trim();
        if (trimmed.split("\\s+").length < 2) {
            throw new IllegalArgumentException("Nome completo deve conter pelo menos nome e sobrenome");
        }
        return new NomeCompleto(trimmed);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NomeCompleto that = (NomeCompleto) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}