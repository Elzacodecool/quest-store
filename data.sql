DROP TABLE IF EXISTS codecooler, admin, mentor, student, mentor_class, class, transaction, inventory, item CASCADE;

CREATE TABLE codecooler (
	id serial PRIMARY KEY,
    first_name text,
    last_name text,
    email text,
    login text,
    password text,
    account_type text
);


INSERT INTO codecooler (First_name, last_name, email, login, password, account_type) 
VALUES ('Eliza', 'Golec', 'email@gmail.com', 'eliza', 'password', 'student');

CREATE TABLE admin (
	id serial PRIMARY KEY,
    user_id integer
);

INSERT INTO admin (user_id)
VALUES (1);

CREATE TABLE mentor (
	id serial PRIMARY KEY,
    user_id integer
);

INSERT INTO mentor (user_id) 
VALUES (1);

CREATE TABLE student (
	id serial PRIMARY KEY,
    user_id integer,
    class_id integer
);

INSERT INTO student (user_id, class_id) 
VALUES (1, 1);

CREATE TABLE class (
	id serial PRIMARY KEY,
    name text
);

INSERT INTO class (name)
VALUES ('webRoom');

CREATE TABLE mentor_class (
    mentor_id integer REFERENCES mentor(id)
             ON UPDATE CASCADE ON DELETE CASCADE,
    class_id integer REFERENCES class(id)
             ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (mentor_id, class_id)
);

INSERT INTO mentor_class (mentor_id, class_id) 
VALUES (1, 1);

CREATE TABLE item (
    id serial PRIMARY KEY,
    name text,
    decription text,
    price integer,
    category text
);

INSERT INTO item (name, decription, price, category) 
VALUES ('name', 'description', 25, 'single');

CREATE TABLE transaction (
    id serial PRIMARY KEY,
    student_id integer REFERENCES student(id)
             ON UPDATE CASCADE ON DELETE CASCADE,
    item_id integer REFERENCES item(id)
             ON UPDATE CASCADE ON DELETE CASCADE,
    amount integer
);


CREATE TABLE inventory (
    student_id integer REFERENCES student(id)
             ON UPDATE CASCADE ON DELETE CASCADE,
    item_id integer REFERENCES item(id)
             ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (student_id, item_id)
);

