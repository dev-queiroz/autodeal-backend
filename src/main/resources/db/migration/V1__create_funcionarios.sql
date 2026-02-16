CREATE TABLE funcionarios (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              nome_completo VARCHAR(255) NOT NULL,
                              cpf VARCHAR(14) NOT NULL UNIQUE,
                              email VARCHAR(255) NOT NULL UNIQUE,
                              senha_hash VARCHAR(255) NOT NULL,
                              perfil_acesso VARCHAR(50) NOT NULL,
                              ativo BOOLEAN NOT NULL DEFAULT TRUE,
                              data_cadastro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);