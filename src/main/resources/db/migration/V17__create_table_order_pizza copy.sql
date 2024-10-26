CREATE TABLE order_pizza (
    order_id BIGINT NOT NULL,
    pizza_id BIGINT NOT NULL,
    registered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  
    foreign key (order_id) references orders(id),
    foreign key (pizza_id) references pizza(id),  
    primary key(order_id, pizza_id)
);

INSERT INTO order_pizza (order_id, pizza_id) VALUES
    (1, 1),
    (1, 2);