CREATE TABLE order(
    id bigint auto_increment,
    fk_client bigint,
    responsible bigint,
    type varchar(45),
    freight double,
    estimated_time decimal(5,2),
    primary key(id),
    foreign key(fk_client) references user(id),
    foreign key (responsible) references user(id)
);

-- just for tests (MOCK) - bad practice
INSERT INTO order (fk_client, responsible, type, freight, estimated_time) VALUES
    ( 2, 1, delivery,10, 40);

INSERT INTO order (fk_client, responsible, type, freight, estimated_time) VALUES
    ( 2, 1, saloon,10, 45);