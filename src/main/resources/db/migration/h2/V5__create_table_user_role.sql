CREATE TABLE user_role (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    date DATE DEFAULT (CURRENT_DATE),
    PRIMARY KEY (user_id, role_id)
);
