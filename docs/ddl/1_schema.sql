CREATE SCHEMA IF NOT EXISTS gitbank CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

USE gitbank;

CREATE TABLE IF NOT EXISTS account
(
    id VARCHAR(255) NOT NULL,
    customer_id VARCHAR(255),
    name VARCHAR(32),
    balance DECIMAL NOT NULL,
    currency VARCHAR(32),
    created_at DATETIME(6),
    updated_at DATETIME(6),
    CONSTRAINT pk_account PRIMARY KEY (id),
    CONSTRAINT uc_account_id UNIQUE (id)
) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS customer
(
    id VARCHAR(255) NOT NULL,
    security_no VARCHAR(11),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    date_of_birth DATE,
    CONSTRAINT pk_customer PRIMARY KEY (id),
    CONSTRAINT uc_customer_id UNIQUE (id),
    CONSTRAINT uc_customer_security_no UNIQUE (security_no)
) ENGINE = INNODB;