package com.autodeal.concessionaria.iam.domain.enums;

public enum PerfilAcesso {

    ADMIN("ROLE_ADMIN"),          // Acesso total (criar funcionários, etc.)
    GERENTE("ROLE_GERENTE"),      // Gerenciar vendas, estoque, clientes
    VENDEDOR("ROLE_VENDEDOR"),    // Registrar vendas, consultar estoque
    FINANCEIRO("ROLE_FINANCEIRO"),// Aprovar financiamentos, ver comissões
    SUPORTE("ROLE_SUPORTE");      // Visualização básica, suporte ao cliente

    private final String roleName;

    PerfilAcesso(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    // Método auxiliar para Spring Security
    public String asGrantedAuthority() {
        return roleName;
    }
}