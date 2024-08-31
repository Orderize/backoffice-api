CREATE TABLE role(
 id bigint auto_increment,
 nome varchar(50) NOT NULL,
 primary key (id)
);

-- just for tests (MOCK) - bad practice
INSERT INTO role (id, nome) VALUES
    (1, 'BACKOFFICE'),
    (2, 'SALOON'),
    (3, 'PIZZA_MAKER'),
    (4, 'CUSTOMER'),
    (5, 'OWNER');