CREATE TABLE accounts
(
    id         BINARY(16)  NOT NULL PRIMARY KEY,
    public_id  BINARY(16)  NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    email      VARCHAR(50) NOT NULL,
    balance    DOUBLE      NOT NULL DEFAULT 0.0
);
CREATE UNIQUE INDEX accounts_public_id_ux ON accounts (public_id);

CREATE TABLE tasks
(
    id          BINARY(16)   NOT NULL PRIMARY KEY,
    public_id   BINARY(16)   NOT NULL,
    description VARCHAR(512) NOT NULL,
    withdraw    DOUBLE       NOT NULL DEFAULT 0.0,
    refill      DOUBLE       NOT NULL DEFAULT 0.0
);
CREATE UNIQUE INDEX tasks_public_id_ux ON tasks (public_id);

CREATE TABLE transactions
(
    id           BINARY(16)  NOT NULL PRIMARY KEY,
    public_id    BINARY(16)  NOT NULL,
    status       VARCHAR(50) NOT NULL,
    type         VARCHAR(50) NOT NULL,
    amount       DOUBLE      NOT NULL DEFAULT 0.0,
    startedAt    DATETIME    NOT NULL,
    completedAt  DATETIME    NOT NULL,
    account_from BINARY(16),
    account_to   BINARY(16),
    CONSTRAINT transactions_from_fk FOREIGN KEY (account_from) REFERENCES accounts (id),
    CONSTRAINT transactions_to_fk FOREIGN KEY (account_to) REFERENCES accounts (id)
);
CREATE UNIQUE INDEX transactions_public_id_ux ON tasks (public_id);

CREATE TABLE payments
(
    id      BINARY(16)  NOT NULL PRIMARY KEY,
    account BINARY(16)  NOT NULL,
    amount  DOUBLE      NOT NULL,
    status  VARCHAR(50) NOT NULL,
    paidAt  DATETIME    NOT NULL
);

CREATE TABLE billing_cycles
(
    id          BINARY(16)  NOT NULL PRIMARY KEY,
    account     BINARY(16)  NOT NULL,
    transaction BINARY(16),
    payment     BINARY(16),
    amount      DOUBLE      NOT NULL,
    status      VARCHAR(50) NOT NULL,
    createdAt   DATETIME    NOT NULL,
    CONSTRAINT billing_cycles_transaction_fk FOREIGN KEY (transaction) REFERENCES transactions (id),
    CONSTRAINT billing_cycles_payment_fk FOREIGN KEY (payment) REFERENCES payments (id),
    CONSTRAINT billing_cycles_account_fk FOREIGN KEY (account) REFERENCES accounts (id)
);
