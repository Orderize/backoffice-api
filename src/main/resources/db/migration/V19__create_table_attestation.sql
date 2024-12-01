CREATE TABLE attestation(
    id bigint primary key auto_increment,
    created_time date default (current_date),
    fk_order bigint,
    foreign key (fk_order) references orders(id)
);

INSERT INTO attestation (fk_order, created_time) VALUES
    (1, CURRENT_TIMESTAMP),
    (1, DATE_SUB(CURRENT_DATE, INTERVAL 1 WEEK)),
    (2, DATE_SUB(CURRENT_DATE, INTERVAL 1 WEEK)),
    (3, DATE_SUB(CURRENT_DATE, INTERVAL 1 WEEK)),
    (4, DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH)),
    (5, DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH)),
    (6, DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH)),
    (7, DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH)),
    (8, DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH)),
    (9, DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH)),
    (10, DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH));