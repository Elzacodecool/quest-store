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
             ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (mentor_id, class_id)
);

CREATE TABLE item (
    id serial PRIMARY KEY,
    name text,
    decription text,
    price integer,
    category text
);

CREATE TABLE transaction (
    student_id integer REFERENCES student(id)
             ON UPDATE CASCADE ON DELETE CASCADE,
    item_id integer REFERENCES item(id)
             ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (student_id, item_id)
);

CREATE TABLE inventory (
    student_id integer REFERENCES student(id)
             ON UPDATE CASCADE ON DELETE CASCADE,
    item_id integer REFERENCES item(id)
             ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (student_id, item_id)
)