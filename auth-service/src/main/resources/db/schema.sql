CREATE TABLE users
(
    id         BINARY(16)  NOT NULL,
    email      VARCHAR(50) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    password   VARCHAR(50) NOT NULL,
    role       VARCHAR(50) NOT NULL,
    deleted    BOOLEAN     NOT NULL,
    PRIMARY KEY (id)
);
