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
    (09941580, "São Paulo", 40, "Rua Amélia Bazzo Falasque", "Diadema", "Canhema"),
    (11045320, "São Paulo", 100, "Rua dos Alfeneiros", "São Paulo", "Centro"),
    (22031010, "Rio de Janeiro", 30, "Avenida Atlântica", "Rio de Janeiro", "Copacabana");
