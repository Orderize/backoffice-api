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
    ("Coca-cola", "Coquinha gelada lata", 8.90, 350);