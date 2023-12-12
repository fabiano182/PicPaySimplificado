CREATE SCHEMA testepicpay;

CREATE TABLE customer
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    nomeCompleto    VARCHAR(256)        NOT NULL,
    registroGoverno VARCHAR(14) UNIQUE  NOT NULL,
    email           VARCHAR(256) UNIQUE NOT NULL,
    senha           VARCHAR(256)        NOT NULL,
    saldo           FLOAT               NOT NULL
);

CREATE TABLE transaction_history
(
    id               INT PRIMARY KEY AUTO_INCREMENT,
    envia            INT   NOT NULL,
    recebe           INT   NOT NULL,
    valor            FLOAT NOT NULL,
    transaction_date DATE
);

ALTER TABLE transaction_history
    ADD CONSTRAINT FK_transaction_history_2
        foreign key (envia, recebe) references customer (id, id);

/*