package com.autodeal.concessionaria.shared.vo;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Cpf {

    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$|^\\d{11}$");

    private final String value;

    private Cpf(String value) {
        this.value = value;
    }

    public static Cpf of(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
        }

        String cleaned = cpf.replaceAll("[^0-9]", "");

        if (cleaned.length() != 11) {
            throw new IllegalArgumentException("CPF deve conter exatamente 11 dígitos numéricos");
        }

        if (!isValidCpf(cleaned)) {
            throw new IllegalArgumentException("CPF inválido");
        }

        // Formata com máscara padrão
        String formatted = cleaned.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        return new Cpf(formatted);
    }

    private static boolean isValidCpf(String cpf) {
        if (cpf.chars().distinct().count() == 1) {
            return false; // 111.111.111-11, 000... etc
        }

        int[] digits = cpf.chars().map(c -> c - '0').toArray();

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += digits[i] * (10 - i);
        }
        int digit1 = (sum * 10) % 11;
        if (digit1 == 10) digit1 = 0;
        if (digit1 != digits[9]) return false;

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += digits[i] * (11 - i);
        }
        int digit2 = (sum * 10) % 11;
        if (digit2 == 10) digit2 = 0;

        return digit2 == digits[10];
    }

    public String getValue() {
        return value;
    }

    public String getCleanValue() {
        return value.replaceAll("[^0-9]", "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cpf cpf = (Cpf) o;
        return value.equals(cpf.value);
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