CREATE TABLE timestamp (
    id                  varchar,
    time                timestamp(0),
    post                int,
    card                varchar(4),
    event               int,
    PRIMARY KEY (id)
);

CREATE TABLE card (
    card                varchar (4),
    first_name          varchar,
    last_name           varchar,
    middle_name         varchar,
    position            varchar,
    PRIMARY KEY (card)
);

CREATE TABLE s_code (
    card                varchar (4),
    s_code              varchar (8),
    PRIMARY KEY (card)
);
