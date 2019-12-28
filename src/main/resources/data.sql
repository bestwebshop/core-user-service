drop table if exists role;
drop table if exists customer;

CREATE TABLE role
(
    id     INT NOT NULL AUTO_INCREMENT,
    level1 INT,
    type   VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE customer
(
    id       INT          NOT NULL AUTO_INCREMENT,
    name     VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    role     INT          NOT NULL,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX UK_mufchskagt7e1w4ksmt9lum5l ON customer (username ASC);

CREATE INDEX FK74aoh99stptslhotgf41fitt0 ON customer (role ASC);

insert into role (level1, type) values(0, 'admin');
insert into role (level1, type) values(1, 'user');

insert into customer (name, lastname, password, username, role) values('admin', 'admin', 'admin', 'admin', 1);
