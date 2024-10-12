CREATE TABLE pizza (
    id_pizza bigint auto_increment not null,
    fk_flavor bigint auto_increment not null,
    name VARCHAR(255) not null,
    price DECIMAL(10, 2) not null,
    observations VARCHAR(300),
    foreign key (fk_flavor) references flavor(id_flavor),
    primary key (id_pizza, fk_flavor)
);

INSERT INTO pizza (fk_flavor, name, price, observations) VALUES 
(1, 'Margherita', 35.90, 'Sem cebola'),
(2, 'Pepperoni', 42.50, 'Com borda recheada de queijo'),
(3, 'Frango com Catupiry', 39.90, 'Sem borda recheada'),
(4, 'Quatro Queijos', 45.00, 'Extra queijo gorgonzola'),
(5, 'Calabresa', 38.50, 'Com cebola e pimentão');