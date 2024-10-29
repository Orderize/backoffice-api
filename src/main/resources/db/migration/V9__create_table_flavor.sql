CREATE TABLE flavor (
    id bigint auto_increment,
    name varchar(90) NOT NULL UNIQUE,
    price DECIMAl(6, 2) NOT NULL,
    description TEXT, 
    registered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,    
    primary key(id)
);

INSERT INTO flavor (name, description, price) VALUES
    ('Margherita', 'Pizza tradicional com molho de tomate, mussarela de búfala e manjericão fresco.', 12.18),
    ('Calabresa', 'Coberta com fatias de calabresa defumada, cebola e azeitonas pretas.', 11.25),
    ('Quatro Queijos', 'Mistura de mussarela, parmesão, gorgonzola e provolone, rica e cremosa.', 9.30),
    ('Frango com Catupiry', 'Frango desfiado com o tradicional requeijão cremoso catupiry e temperos.', 13.40),
    ('Portuguesa', 'Pizza com presunto, ovos, cebola, azeitonas, ervilhas e mussarela.', 10.23),
    ('Pepperoni', 'Coberta com fatias de pepperoni levemente picante e queijo mussarela.', 13.09),
    ('Toscana', 'Calabresa toscana fatiada, cebola e um toque de orégano.', 11.68),
    ('Vegetariana', 'Pizza com vegetais frescos, como tomate, pimentão, cebola, cogumelos e azeitonas.', 11.53),
    ('Baiana', 'Carne moída temperada, ovos, pimenta e cebola, criando um sabor picante.', 12.47),
    ('Atum', 'Coberta com atum, cebola, azeitonas e mussarela.', 11.73),
    ('Alho e Óleo', 'Coberta com alho fatiado e mussarela, simples e deliciosa.', 15.06),
    ('Chocolate', 'Pizza doce com cobertura de chocolate derretido, ideal para sobremesas.', 17.52);
