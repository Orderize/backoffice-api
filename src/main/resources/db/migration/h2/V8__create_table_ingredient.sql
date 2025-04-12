CREATE TABLE ingredient (
    id bigint auto_increment,
    name VARCHAR(255) NOT NULL,
    paid DECIMAL(5, 2),
    primary key(id)
);

INSERT INTO ingredient (name, paid) VALUES
    ('Mozzarella Cheese', 1.50),
    ('Pepperoni', 2.00),
    ('Mushrooms', 1.20),
    ('Onions', 0.80),
    ('Green Peppers', 1.00),
    ('Black Olives', 1.30),
    ('Sausage', 2.20),
    ('Bacon', 2.50),
    ('Tomato Sauce', 0.90),
    ('Garlic', 0.60),
    ('Basil', 0.40),
    ('Pineapple', 1.80),
    ('Chicken', 2.30),
    ('Ham', 2.10),
    ('Spinach', 1.10);
