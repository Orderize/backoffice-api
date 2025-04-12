CREATE TABLE pizza (
    id bigint auto_increment not null,
    name VARCHAR(255) not null,
    price DECIMAL(10, 2) not null,
    observations VARCHAR(300),
    border VARCHAR(100),
    size VARCHAR(100),
    mass VARCHAR(100),
    primary key(id)
);

INSERT INTO pizza (name, price, observations, border, size, mass) VALUES 
('Margherita', 35.90, 'Sem cebola', 'Sem borda', 'Média', 'Fina'),
('Pepperoni', 42.50, 'Com borda recheada de queijo', 'Cheddar', 'Grande', 'Tradicional'),
('Frango com Catupiry', 39.90, 'Sem borda recheada', 'Sem borda', 'Pequena', 'Fina'),
('Quatro Queijos', 45.00, 'Extra queijo gorgonzola', 'Cheddar', 'Grande', 'Tradicional'),
('Calabresa', 38.50, 'Com cebola e pimentão', 'Sem borda', 'Média', 'Fina');