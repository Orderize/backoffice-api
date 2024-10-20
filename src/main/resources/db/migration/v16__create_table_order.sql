CREATE TABLE order(
    id bigint auto_increment,
    fk_client bigint,
    responsible bigint,
    datetime_order timestamp default current_timestamp,
    type varchar(45),
    freight decimal(5,2),
    estimated_time double,
    grossPrice double,
    netPrice double,

    primary key(id),
    foreign key(fk_client) references user(id),
    foreign key (responsible) references user(id)
);

-- just for tests (MOCK) - bad practice
INSERT INTO order (fk_client, responsible, type, freight, estimated_time, grossPrice, netPrice) VALUES
    ( 2, 1,delivery,5.50, 45.0, 50.00, 45.00);

INSERT INTO order (fk_client, responsible, type, freight, estimated_time, grossPrice, netPrice) VALUES
    ( 2, 1, saloon,5.50, 30.0, 75.00, 70.00);