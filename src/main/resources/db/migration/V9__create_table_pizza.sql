CREATE TABLE pizza (
    id_pizza bigint auto_increment not null,
    fk_flavor bigint auto_increment not null,
    name VARCHAR(255) not null,
    price DECIMAL(10, 2) not null,
    observations VARCHAR(300),
    foreign key (fk_flavor) references flavor(id_flavor),
    primary key (id_pizza, fk_flavor)
);
