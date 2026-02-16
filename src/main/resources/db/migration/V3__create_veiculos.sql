CREATE TABLE veiculos (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          chassi VARCHAR(50) NOT NULL UNIQUE,
                          placa VARCHAR(10) NOT NULL UNIQUE,
                          marca VARCHAR(100) NOT NULL,
                          modelo VARCHAR(100) NOT NULL,
                          ano_fabricacao INT NOT NULL,
                          ano_modelo INT NOT NULL,
                          cor VARCHAR(50) NOT NULL,
                          quilometragem INT NOT NULL,
                          estado VARCHAR(50) NOT NULL,
                          preco_compra DECIMAL(15,2) NOT NULL,
                          preco_venda_sugerido DECIMAL(15,2) NOT NULL,
                          fornecedor_id BIGINT,
                          data_entrada_estoque DATE NOT NULL DEFAULT CURRENT_DATE,
                          ativo BOOLEAN NOT NULL DEFAULT TRUE,
                          FOREIGN KEY (fornecedor_id) REFERENCES fornecedores(id)
);