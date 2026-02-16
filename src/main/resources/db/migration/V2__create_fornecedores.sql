CREATE TABLE fornecedores (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              nome VARCHAR(255) NOT NULL,
                              cnpj VARCHAR(18) NOT NULL UNIQUE,
                              email VARCHAR(255) NOT NULL UNIQUE,
                              telefone VARCHAR(20),
                              contato_responsavel VARCHAR(100),
                              ativo BOOLEAN NOT NULL DEFAULT TRUE
);