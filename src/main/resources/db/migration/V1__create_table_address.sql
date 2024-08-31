CREATE TABLE address (
    id bigint auto_increment,
    cep varchar(8) not null,
    state varchar(45) not null,
    number int not null,
    street varchar(45),
    city varchar(45),
    neighborhood varchar(45),
    primary key(id)
);

-- just for tests (MOCK) - bad practice
INSERT INTO address (cep, state, number, street, city, neighborhood) VALUES
    (09941580, "São Paulo", 40, "Rua Amélia Bazzo Falasque", "Diadema", "Canhema");