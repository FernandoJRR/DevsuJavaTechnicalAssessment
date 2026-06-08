-- Schema for clienteMicro (db_clientes)
-- Database is created by Docker via POSTGRES_DB env var; only the table is needed here.

CREATE TABLE IF NOT EXISTS clients (
    client_id      UUID            PRIMARY KEY DEFAULT gen_random_uuid(),
    name           VARCHAR(255)    NOT NULL,
    gender         VARCHAR(50),
    age            INTEGER,
    identification VARCHAR(100),
    address        VARCHAR(255),
    phone          VARCHAR(50),
    password       VARCHAR(255)    NOT NULL,
    active         BOOLEAN         NOT NULL DEFAULT TRUE
);
