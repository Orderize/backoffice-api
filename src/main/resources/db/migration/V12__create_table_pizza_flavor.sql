CREATE TABLE pizza_flavor (
    pizza_id BIGINT NOT NULL,
    flavor_id BIGINT NOT NULL,
    registered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  
    foreign key (pizza_id) references pizza(id),
    foreign key (flavor_id) references flavor(id),  
    primary key(pizza_id, flavor_id)
);

INSERT INTO pizza_flavor (pizza_id, flavor_id) VALUES
    (1, 1),
    (1, 2),
    (2, 1),
    (2, 2);