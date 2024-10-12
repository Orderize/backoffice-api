CREATE TABLE flavor (
    id bigint auto_increment,
    name varchar(90) NOT NULL UNIQUE,
    description TEXT, 
    registered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,    
    primary key(id)
);

INSERT INTO flavor (name, description) VALUES
    ('Margherita', 'Pizza tradicional com molho de tomate, mussarela de búfala e manjericão fresco.'),
    ('Calabresa', 'Coberta com fatias de calabresa defumada, cebola e azeitonas pretas.'),
    ('Quatro Queijos', 'Mistura de mussarela, parmesão, gorgonzola e provolone, rica e cremosa.'),
    ('Frango com Catupiry', 'Frango desfiado com o tradicional requeijão cremoso catupiry e temperos.'),
    ('Portuguesa', 'Pizza com presunto, ovos, cebola, azeitonas, ervilhas e mussarela.'),
    ('Pepperoni', 'Coberta com fatias de pepperoni levemente picante e queijo mussarela.'),
    ('Toscana', 'Calabresa toscana fatiada, cebola e um toque de orégano.'),
    ('Vegetariana', 'Pizza com vegetais frescos, como tomate, pimentão, cebola, cogumelos e azeitonas.'),
    ('Baiana', 'Carne moída temperada, ovos, pimenta e cebola, criando um sabor picante.'),
    ('Atum', 'Coberta com atum, cebola, azeitonas e mussarela.'),
    ('Alho e Óleo', 'Coberta com alho fatiado e mussarela, simples e deliciosa.'),
    ('Chocolate', 'Pizza doce com cobertura de chocolate derretido, ideal para sobremesas.');
