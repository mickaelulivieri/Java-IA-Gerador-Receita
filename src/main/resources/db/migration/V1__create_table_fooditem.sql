CREATE TABLE food_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    quantidade INT,
    validade DATETIME,
    CHECK (categoria IN (
        'LATICINIO', 'PROTEINA', 'BEBIDAS', 'LIPIDIOS',
        'CARNES', 'FRUTAS', 'OVOS', 'MASSAS'
    ))
);