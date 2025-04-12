CREATE TABLE flavor_ingredient (
    flavor_id BIGINT NOT NULL,
    ingredient_id BIGINT NOT NULL,
    registered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,    
    FOREIGN KEY (flavor_id) REFERENCES flavor(id),
    FOREIGN KEY (ingredient_id) REFERENCES ingredient(id),
    PRIMARY KEY(flavor_id, ingredient_id)
);

INSERT INTO flavor_ingredient (flavor_id, ingredient_id) VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 1);