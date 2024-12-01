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
INSERT INTO orders (fk_client, fk_responsible, type, freight, estimated_time, price, datetime)
VALUES
    (1, 2, 'delivery', 5.00, 30, 85.00, CURRENT_TIMESTAMP),
    (1, 2, 'delivery', 5.00, 50, 85.00, DATE_SUB(CURRENT_DATE, INTERVAL 1 WEEK)),
    (1, 2, 'saloon', 6.00, 30, 95.00, DATE_SUB(CURRENT_DATE, INTERVAL 1 WEEK)),
    (2, 1, 'delivery', 4.50, 55, 90.00, DATE_SUB(CURRENT_DATE, INTERVAL 1 WEEK)),
    (2, 1, 'saloon', 8.00, 40, 120.00, DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH)),
    (1, 2, 'delivery', 4.50, 45, 55.00, DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH)),
    (1, 2, 'saloon', 6.50, 35, 90.00, DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH)),
    (2, 1, 'delivery', 5.00, 60, 110.00, DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH)),
    (1, 2, 'saloon', 5.75, 50, 95.00, DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH)),
    (2, 1, 'delivery', 7.50, 45, 60.00, DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH)),
    (2, 1, 'saloon', 4.00, 40, 80.00, DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH));
