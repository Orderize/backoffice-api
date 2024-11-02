CREATE TABLE order_drink (
    order_id BIGINT NOT NULL,
    drink_id BIGINT NOT NULL,
    registered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  
    foreign key (order_id) references orders(id),
    foreign key (drink_id) references drink(id),  
    primary key(order_id, drink_id)
);

INSERT INTO order_drink (order_id, drink_id) VALUES
    (1, 1),
    (2, 1);