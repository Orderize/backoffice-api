CREATE TABLE condition_type(
    id bigint auto_increment,
    description varchar(200),
    primary key(id)
);

INSERT INTO condition_type (description) VALUES
    ('Pedido conter bebidas'),
    ('Pedido conter pizzas'),
    ('Valor mínimo de pedido');