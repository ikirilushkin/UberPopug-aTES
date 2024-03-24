CREATE TABLE users
(
    id         BINARY(16)  NOT NULL PRIMARY KEY,
    public_id  BINARY(16)  NOT NULL,
    email      VARCHAR(50) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    password   VARCHAR(50) NOT NULL,
    role       VARCHAR(50) NOT NULL,
    deleted    BOOLEAN     NOT NULL
);
CREATE UNIQUE INDEX users_public_id_ux ON users (public_id);

INSERT INTO users
    (id, public_id, email, first_name, last_name, password, role, deleted)
VALUES (uuid_to_bin(uuid()), uuid_to_bin(uuid()), 'admin@mail.com', 'test', 'admin', 'password', 'ADMINISTRATOR',
        false);
