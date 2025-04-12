CREATE TABLE attestation(
    id bigint primary key auto_increment,
    created_time date default (current_date),
    fk_order bigint,
    foreign key (fk_order) references orders(id)
);

INSERT INTO attestation (fk_order, created_time) VALUES
    (1, CURRENT_TIMESTAMP),
    (1, CURRENT_DATE - 7),
    (2, CURRENT_DATE - 7),
    (3, CURRENT_DATE - 7),
    (4, CURRENT_DATE - 30),
    (5, CURRENT_DATE - 30),
    (6, CURRENT_DATE - 30),
    (7, CURRENT_DATE - 30),
    (8, CURRENT_DATE - 30),
    (9, CURRENT_DATE - 30),
    (10, CURRENT_DATE - 30);