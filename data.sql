DROP TABLE IF EXISTS codecooler, admin, mentor, student, mentor_class, class, transaction, inventory, item CASCADE;

CREATE TABLE codecooler (
	codecooler_id serial PRIMARY KEY,
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
	admin_id serial PRIMARY KEY,
    codecooler_id integer,
    FOREIGN KEY (codecooler_id) REFERENCES codecooler (codecooler_id)
		ON DELETE CASCADE ON UPDATE NO ACTION
);

INSERT INTO admin (codecooler_id)
VALUES (1);

CREATE TABLE mentor (
	mentor_id serial PRIMARY KEY,
    codecooler_id integer,
    FOREIGN KEY (codecooler_id) REFERENCES codecooler (codecooler_id)
		ON DELETE CASCADE ON UPDATE NO ACTION
);

INSERT INTO mentor (codecooler_id) 
VALUES (1);

CREATE TABLE class (
	class_id serial PRIMARY KEY,
    name text
);

INSERT INTO class (name)
VALUES ('webRoom');

CREATE TABLE student (
	student_id serial PRIMARY KEY,
    codecooler_id integer,
    class_id integer,
    FOREIGN KEY (codecooler_id) REFERENCES codecooler (codecooler_id)
		ON DELETE CASCADE ON UPDATE NO ACTION,
    FOREIGN KEY (class_id) REFERENCES class (class_id)
		ON DELETE CASCADE ON UPDATE NO ACTION
);

INSERT INTO student (codecooler_id, class_id) 
VALUES (1, 1);

CREATE TABLE mentor_class (
    mentor_id integer, 
    class_id integer ,
    PRIMARY KEY (mentor_id, class_id),
    FOREIGN KEY (mentor_id) REFERENCES mentor (mentor_id)
		ON DELETE CASCADE ON UPDATE NO ACTION,
    FOREIGN KEY (class_id) REFERENCES class (class_id)
		ON DELETE CASCADE ON UPDATE NO ACTION
);

INSERT INTO mentor_class (mentor_id, class_id) 
VALUES (1, 1);

CREATE TABLE item (
    item_id serial PRIMARY KEY,
    name text,
    description text,
    price integer,
    category text
);

INSERT INTO item (name, decription, price, category) 
VALUES ('name', 'description', 25, 'single');

CREATE TABLE transaction (
    transaction_id serial PRIMARY KEY,
    student_id integer,
    item_id integer,
    amount integer,
    FOREIGN KEY (student_id) REFERENCES student (student_id)
		ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (item_id) REFERENCES item (item_id)
		ON DELETE NO ACTION ON UPDATE NO ACTION
);


CREATE TABLE inventory (
    student_id integer,
    item_id integer,
    PRIMARY KEY (student_id, item_id),
    FOREIGN KEY (student_id) REFERENCES student (student_id)
		ON DELETE CASCADE ON UPDATE NO ACTION,
    FOREIGN KEY (item_id) REFERENCES item (item_id)
		ON DELETE NO ACTION ON UPDATE NO ACTION
);

