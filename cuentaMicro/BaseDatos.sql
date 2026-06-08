-- Schema

CREATE TABLE IF NOT EXISTS accounts (
    account_number  VARCHAR(100)    PRIMARY KEY,
    account_type    VARCHAR(50)     NOT NULL,
    initial_balance NUMERIC(19, 2)  NOT NULL,
    current_balance NUMERIC(19, 2)  NOT NULL,
    active          BOOLEAN         NOT NULL DEFAULT TRUE,
    client_id       UUID
);

CREATE TABLE IF NOT EXISTS transactions (
    id               UUID            PRIMARY KEY DEFAULT gen_random_uuid(),
    date             TIMESTAMP       NOT NULL,
    transaction_type VARCHAR(50)     NOT NULL,
    amount           NUMERIC(19, 2)  NOT NULL,
    balance          NUMERIC(19, 2)  NOT NULL,
    account_number   VARCHAR(100)    NOT NULL REFERENCES accounts(account_number)
);

CREATE TABLE IF NOT EXISTS clients_info (
    client_id   UUID            PRIMARY KEY,
    name        VARCHAR(255)    NOT NULL,
    active      BOOLEAN         NOT NULL DEFAULT TRUE
);
