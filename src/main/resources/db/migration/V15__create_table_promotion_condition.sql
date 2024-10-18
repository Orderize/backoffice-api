CREATE TABLE promotion_condition(
    fk_promotion bigint,
    fk_condition_type bigint,
    required_value decimal(10,2),
    ids_required_itens varchar(300),
    primary key (fk_promotion, fk_condition_type),
    foreign key (fk_promotion) references promotion(id),
    foreign key (fk_condition_type) references condition_type(id)
);

INSERT INTO promotion_condition (fk_promotion, fk_condition_type, required_value, ids_required_itens) VALUES
    (1, 1, null, "1"),
    (1, 3, 50.00, null);