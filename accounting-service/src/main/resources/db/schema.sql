CREATE TABLE users
(
    id         BINARY(16)  NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    email      VARCHAR(50) NOT NULL,
    role       VARCHAR(50) NOT NULL
);

CREATE TABLE tasks
(
    id          BINARY(16)   NOT NULL,
    assignee    BINARY(16)   NOT NULL,
    reporter    BINARY(16)   NOT NULL,
    description VARCHAR(512) NOT NULL,
    status      VARCHAR(50)  NOT NULL,
    created_at  DATETIME     NOT NULL,
    CONSTRAINT tasks_assignee_fk FOREIGN KEY (assignee) REFERENCES users (id),
    CONSTRAINT tasks_reporter_fk FOREIGN KEY (reporter) REFERENCES users (id)
);


