package com.autodeal.concessionaria.shared.vo;

import java.util.Objects;
import java.util.regex.Pattern;

public final class PlacaVeicular {

    // Aceita tanto o padrão antigo (AAA-9999) quanto Mercosul (AAA9A99)
    private static final Pattern PLACA_PATTERN = Pattern.compile(
            "^[A-Z]{3}-?\\d{4}$|^[A-Z]{3}\\d[A-Z]\\d{2}$",
            Pattern.CASE_INSENSITIVE
    );

    private final String value;

    private PlacaVeicular(String value) {
        this.value = value.toUpperCase();
    }

    public static PlacaVeicular of(String placa) {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("Placa não pode ser nula ou vazia");
        }

        String cleaned = placa.replaceAll("[^A-Za-z0-9]", "").toUpperCase();

        if (!PLACA_PATTERN.matcher(cleaned).matches()) {
            throw new IllegalArgumentException("Formato de placa inválido: " + placa);
        }

        // Formata com hífen se for o padrão antigo
        if (cleaned.length() == 7 && Character.isDigit(cleaned.charAt(3))) {
            cleaned = cleaned.substring(0, 3) + "-" + cleaned.substring(3);
        }

        return new PlacaVeicular(cleaned);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlacaVeicular that = (PlacaVeicular) o;
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