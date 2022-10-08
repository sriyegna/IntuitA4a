DROP TABLE IF EXISTS TBL_USERS;

CREATE TABLE TBL_USERS
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(250) NOT NULL,
    password   VARCHAR(250) NOT NULL,
    actor_type VARCHAR(250) NOT NULL
);

DROP TABLE IF EXISTS TBL_JOBS;

CREATE TABLE TBL_JOBS
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id         BIGINT       NOT NULL,
    description     TEXT(16384) NOT NULL,
    requirements    TEXT(16384) NOT NULL,
    name            VARCHAR(250) NOT NULL,
    contact         VARCHAR(250) NOT NULL,
    lowest_bid      REAL,
    number_of_bids  INT          NOT NULL,
    expiration_time TIMESTAMP    NOT NULL,
    created         TIMESTAMP    NOT NULL,
    winner          VARCHAR(250),
    closed          BOOLEAN      NOT NULL
);

DROP TABLE IF EXISTS TBL_BIDS;

CREATE TABLE TBL_BIDS
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT    NOT NULL,
    job_id     BIGINT    NOT NULL,
    bid_amount REAL      NOT NULL,
    created    TIMESTAMP NOT NULL
);