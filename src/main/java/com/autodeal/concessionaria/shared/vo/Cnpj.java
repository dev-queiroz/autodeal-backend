package com.autodeal.concessionaria.shared.vo;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Cnpj {

    private static final Pattern CNPJ_PATTERN = Pattern.compile(
            "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$|^\\d{14}$"
    );

    private final String value;

    private Cnpj(String value) {
        this.value = value;
    }

    public static Cnpj of(String cnpj) {
        if (cnpj == null || cnpj.trim().isEmpty()) {
            throw new IllegalArgumentException("CNPJ não pode ser nulo ou vazio");
        }

        String cleaned = cnpj.replaceAll("[^0-9]", "");

        if (cleaned.length() != 14) {
            throw new IllegalArgumentException("CNPJ deve conter exatamente 14 dígitos numéricos");
        }

        if (!isValidCnpj(cleaned)) {
            throw new IllegalArgumentException("CNPJ inválido");
        }

        // Formata com máscara padrão
        String formatted = cleaned.replaceFirst(
                "(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})",
                "$1.$2.$3/$4-$5"
        );
        return new Cnpj(formatted);
    }

    private static boolean isValidCnpj(String cnpj) {
        if (cnpj.chars().distinct().count() == 1) {
            return false; // 00.000.000/0000-00, 111... etc
        }

        int[] digits = cnpj.chars().map(c -> c - '0').toArray();

        // Primeiro dígito verificador
        int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += digits[i] * weights1[i];
        }
        int digit1 = 11 - (sum % 11);
        if (digit1 >= 10) digit1 = 0;
        if (digit1 != digits[12]) return false;

        // Segundo dígito verificador
        int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        sum = 0;
        for (int i = 0; i < 13; i++) {
            sum += digits[i] * weights2[i];
        }
        int digit2 = 11 - (sum % 11);
        if (digit2 >= 10) digit2 = 0;

        return digit2 == digits[13];
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
        Cnpj cnpj = (Cnpj) o;
        return value.equals(cnpj.value);
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