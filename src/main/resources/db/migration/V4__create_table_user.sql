CREATE TABLE user (
    id bigint auto_increment,
    name varchar(90),
    email varchar(90) UNIQUE,
    password varchar(200),
    phone varchar(45),
    fk_address bigint,
    fk_enterprise bigint,
    primary key(id),
    foreign key(fk_address) references address(id),
    foreign key(fk_enterprise) references enterprise(id)
);

-- just for tests (MOCK) - bad practice
-- kotlin 12345A
-- java Senha123
INSERT INTO user (name, email, password, phone, fk_address, fk_enterprise) VALUES
    ("Kotlinator of metal", "kotlin@gmail.com", "$2a$12$O.VBqUN3tI8KSNPkR/ZNdeg0tj1Zr5Fq5SVKL3WqjEAtwss/OjM3y", "11945064629", 1, 1),
    ("Knight Java", "java@gmail.com", "$2a$12$NG6Z1iHH7DFQrk45kucFsO3EbbXuO3wRJh44fi3AsEDHSiLz2ro16", "1194773942", 1, 1);
