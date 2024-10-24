CREATE TABLE order_drink (
    fk_order bigint,
    fk_drink bigint,
    quantity int,
    gross_price decimal(10,2),
    net_price decimal(10,2),

    primary key (fk_order, fk_drink),
    foreign key (fk_order) references order(id),
    foreign key (fk_drink) references drink(id)
);

-- just for tests (MOCK) - bad practice
INSERT INTO order_drink (fk_order, fk_drink, quantity, gross_price, net_price) VALUES
    (1, 3, 2, 15.0, 14.0),
    (2, 1, 1, 10.00, 9.50);