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
    (1, 2),
    (2, 3), 
    (3, 2), 
    (3, 4),  
    (4, 5), 
    (5, 1), 
    (6, 4),  
    (6, 2), 
    (7, 3), 
    (8, 5), 
    (9, 2),
    (10, 1);