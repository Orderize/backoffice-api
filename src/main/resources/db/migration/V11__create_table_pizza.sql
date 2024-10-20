CREATE TABLE pizza (
    id bigint auto_increment not null,
    name VARCHAR(255) not null,
    price DECIMAL(10, 2) not null,
    observations VARCHAR(300),
    primary key(id)
);

INSERT INTO pizza (name, price, observations) VALUES 
('Margherita', 35.90, 'Sem cebola'),
('Pepperoni', 42.50, 'Com borda recheada de queijo'),
('Frango com Catupiry', 39.90, 'Sem borda recheada'),
('Quatro Queijos', 45.00, 'Extra queijo gorgonzola'),
('Calabresa', 38.50, 'Com cebola e pimentão');