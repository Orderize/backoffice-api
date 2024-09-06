CREATE TABLE enterprise (
    id bigint auto_increment,
    name varchar(50),
    cnpj varchar(18),
    primary key (id)
);

-- just for tests (MOCK) - bad practice
INSERT INTO enterprise VALUES
    (1, 'Testingzzaria', '00.493.561/0001-70');