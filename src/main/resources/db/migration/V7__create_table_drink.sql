CREATE TABLE drink (
    id bigint auto_increment,
    name varchar(50) not null,
    description varchar(90) not null,
    price decimal(20, 2) not null,
    milimeters int not null,
    primary key(id)
);

-- just for tests (MOCK) - bad practice
INSERT INTO drink (name, description, price, milimeters) VALUES
    ("Coca-cola", "Coquinha gelada lata", 8.90, 350),
    ("Sprite", "Lata de Sprite gelada", 7.90, 350),
    ("Guaraná", "Guaraná Antarctica", 8.50, 350),
    ("Água", "Garrafa de água mineral", 4.00, 500);