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
	id serial primary key,
    user_id integer
);

CREATE TABLE student (
	id serial primary key,
    user_id integer
    class_id integer
);
