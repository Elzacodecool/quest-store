DROP TABLE IF EXISTS codecooler, admin, mentor, student, mentor_class, class, transaction, item;

CREATE TABLE codecooler (
	id serial PRIMARY KEY,
    first_name text,
    last_name text,
    email text,
    login text,
    password text,
    account_type text
);

CREATE TABLE admin (
	id serial PRIMARY KEY,
    user_id integer
);

CREATE TABLE mentor (
	id serial PRIMARY KEY,
    user_id integer
);

CREATE TABLE student (
	id serial PRIMARY KEY,
    user_id integer,
    class_id integer
);

CREATE TABLE class (
	id serial PRIMARY KEY,
    name text
);

CREATE TABLE mentor_class (
    mentor_id integer REFERENCES mentor(id)
             ON UPDATE CASCADE ON DELETE CASCADE,
    class_id integer REFERENCES class(id)
             ON UPDATE CASCADE ON DELETE CASCADE
);