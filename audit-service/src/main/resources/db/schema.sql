CREATE TABLE users
(
    id         BINARY(16)  NOT NULL PRIMARY KEY,
    public_id  BINARY(16)  NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    email      VARCHAR(50) NOT NULL,
    role       VARCHAR(50) NOT NULL
);
CREATE UNIQUE INDEX users_public_id_ux ON users (public_id);

