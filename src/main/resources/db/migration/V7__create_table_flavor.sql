CREATE TABLE flavor (
    id_flavor bigint not null,
    name varchar(42) not null,
    description text,
    registered timestamp default current_timestamp()
);

