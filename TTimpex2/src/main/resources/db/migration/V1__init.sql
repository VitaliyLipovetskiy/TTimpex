CREATE TABLE s_code (
    card                varchar (4) PRIMARY KEY,
    s_code              varchar (8)
);
CREATE TABLE card (
    card                varchar (4) PRIMARY KEY,
    first_name          varchar,
    last_name           varchar,
    middle_name         varchar,
    position            varchar--,
--     FOREIGN KEY (card) REFERENCES s_code(card)
);
CREATE TABLE timestamp (
    id                  VARCHAR PRIMARY KEY,
    date_time           TIMESTAMP,
    post                INTEGER,
    card                VARCHAR(4),
    event               INTEGER--,
--     FOREIGN KEY (card) REFERENCES card(card)
);