CREATE TABLE promotion(
    id bigint auto_increment,
    name varchar(90) not null,
    description varchar(300),
    discount_value decimal(10,2),
    start_date date default (current_date),
    end_date date default (current_date),
    primary key (id)
);

INSERT INTO promotion(name, description, discount_value, end_date) VALUES
    ('Frango maluco acima de R$100', 'Ganha uma brotinho de frango e um desconto', 5.00, '2025-07-01'),
    ('Quarta-feira Maluca', 'Desconto de 10% em todas as pizzas', 10.00, '2025-12-31'),
    ('Combo Família', 'Duas pizzas grandes e dois refrigerantes por R$ 80', 20.00, '2026-06-15'),
    ('Desconto delivery', 'Entrega grátis para pedidos acima de R$ 50', 0.00, '2025-11-20');
