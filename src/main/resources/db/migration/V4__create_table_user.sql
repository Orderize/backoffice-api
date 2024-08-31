CREATE TABLE user (
    id bigint auto_increment,
    name varchar(90),
    email varchar(90),
    password varchar(45),
    phone varchar(45),
    fk_address bigint,
    fk_enterprise bigint,
    primary key(id),
    foreign key(fk_address) references address(id),
    foreign key(fk_enterprise) references enterprise(id)
);

-- just for tests (MOCK) - bad practice
INSERT INTO user (name, email, password, phone, fk_address, fk_enterprise) VALUES
    ("Kotlinator of metal", "kotlin@gmail.com", "12345A", "11945064629", 1, 1);