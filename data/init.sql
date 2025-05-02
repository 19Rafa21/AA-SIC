DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100),
    password VARCHAR(100),
    email VARCHAR(255),
    role VARCHAR(100)
);