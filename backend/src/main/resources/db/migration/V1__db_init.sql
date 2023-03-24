CREATE SEQUENCE IF NOT EXISTS sequence_role START WITH 1 INCREMENT BY 5;

CREATE SEQUENCE IF NOT EXISTS sequence_transaction START WITH 1 INCREMENT BY 5;

CREATE SEQUENCE IF NOT EXISTS sequence_type START WITH 1 INCREMENT BY 5;

CREATE SEQUENCE IF NOT EXISTS sequence_wallet START WITH 1 INCREMENT BY 5;

CREATE SEQUENCE IF NOT EXISTS public.sequence_user START WITH 1 INCREMENT BY 5;

CREATE TABLE role
(
    id   BIGINT      NOT NULL,
    type VARCHAR(20) NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE transaction
(
    id               BIGINT                      NOT NULL,
    amount           DECIMAL                     NOT NULL,
    description      VARCHAR(50),
    date             TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    reference_number VARCHAR(12)                 NOT NULL,
    status           VARCHAR(20)                 NOT NULL,
    from_wallet_id   BIGINT                      NOT NULL,
    to_wallet_id     BIGINT                      NOT NULL,
    type_id          BIGINT                      NOT NULL,
    CONSTRAINT pk_transaction PRIMARY KEY (id)
);

CREATE TABLE type
(
    id          BIGINT      NOT NULL,
    name        VARCHAR(50) NOT NULL,
    description VARCHAR(50),
    CONSTRAINT pk_type PRIMARY KEY (id)
);

CREATE TABLE wallet
(
    id      BIGINT      NOT NULL,
    iban    VARCHAR(26) NOT NULL,
    name    VARCHAR(50) NOT NULL,
    balance DECIMAL     NOT NULL,
    user_id BIGINT      NOT NULL,
    CONSTRAINT pk_wallet PRIMARY KEY (id)
);

CREATE TABLE public."user"
(
    id         BIGINT       NOT NULL,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    username   VARCHAR(20)  NOT NULL,
    email      VARCHAR(50)  NOT NULL,
    password   VARCHAR(100) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE public.user_role
(
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_user_role PRIMARY KEY (role_id, user_id)
);

ALTER TABLE role
    ADD CONSTRAINT uc_role_type UNIQUE (type);

ALTER TABLE transaction
    ADD CONSTRAINT uc_transaction_referencenumber UNIQUE (reference_number);

ALTER TABLE type
    ADD CONSTRAINT uc_type_name UNIQUE (name);

ALTER TABLE wallet
    ADD CONSTRAINT uc_wallet_iban UNIQUE (iban);

ALTER TABLE public."user"
    ADD CONSTRAINT uc_user_email UNIQUE (email);

ALTER TABLE public."user"
    ADD CONSTRAINT uc_user_username UNIQUE (username);

CREATE INDEX wallet_iban_idx ON wallet (iban);

CREATE UNIQUE INDEX wallet_user_id_iban_key ON wallet (user_id, iban);

CREATE UNIQUE INDEX wallet_user_id_name_key ON wallet (user_id, name);

ALTER TABLE transaction
    ADD CONSTRAINT FK_TRANSACTION_ON_FROM_WALLET FOREIGN KEY (from_wallet_id) REFERENCES wallet (id);

ALTER TABLE transaction
    ADD CONSTRAINT FK_TRANSACTION_ON_TO_WALLET FOREIGN KEY (to_wallet_id) REFERENCES wallet (id);

ALTER TABLE transaction
    ADD CONSTRAINT FK_TRANSACTION_ON_TYPE FOREIGN KEY (type_id) REFERENCES type (id);

ALTER TABLE wallet
    ADD CONSTRAINT FK_WALLET_ON_USER FOREIGN KEY (user_id) REFERENCES public."user" (id);

ALTER TABLE public.user_role
    ADD CONSTRAINT fk_user_role_on_role FOREIGN KEY (role_id) REFERENCES role (id);

ALTER TABLE public.user_role
    ADD CONSTRAINT fk_user_role_on_user FOREIGN KEY (user_id) REFERENCES public."user" (id);