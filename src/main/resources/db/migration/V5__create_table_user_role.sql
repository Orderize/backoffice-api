CREATE TABLE user_role (
    user_id bigint not null,
    role_id bigint not null,
    date date default current_date(),
    primary key (user_id, role_id)
);