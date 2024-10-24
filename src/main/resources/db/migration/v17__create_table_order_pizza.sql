CREATE TABLE order_pizza (
    fk_order bigint,
    fk_pizza bigint,
    quantity int,
    gross_price decimal(10,2),
    net_price decimal(10,2),

    primary key (fk_order, fk_pizza),
    foreign key (fk_order) references order(id),
    foreign key (fk_pizza) references pizza(id)
);

-- just for tests (MOCK) - bad practice
INSERT INTO order_pizza (fk_order, fk_pizza, quantity, gross_price, net_price) VALUES
    (1, 2, 3, 45.0, 40.0),
    (2, 1, 2, 30.0, 28,0);