CREATE TABLE attestation(
    id bigint primary key auto_increment,
    created_time date default (current_date),
    fk_order bigint,
    foreign key (fk_order) references orders(id)
);

INSERT INTO attestation (fk_order) VALUES
    (1);