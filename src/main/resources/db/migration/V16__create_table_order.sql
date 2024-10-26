CREATE TABLE orders(
    id bigint auto_increment,
    fk_client bigint,
    fk_responsible bigint,
    datetime timestamp default current_timestamp,
    type varchar(45),
    freight decimal(5,2),
    estimated_time INT,
    price decimal(20, 2) not null,
    primary key(id),
    foreign key(fk_client) references user(id),
    foreign key (fk_responsible) references user(id)
);

-- just for tests (MOCK) - bad practice
INSERT INTO orders (fk_client, fk_responsible, type, freight, estimated_time, price) VALUES
    ( 2, 1, 'delivery', 5.50, 45, 50.00),
    ( 1, 2, 'saloon', 5.50, 30, 75.00);
