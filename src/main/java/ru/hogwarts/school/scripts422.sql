CREATE TABLE people (
    id INTEGER PRIMARY KEY,
    name TEXT,
    age INTEGER,
    has_license BOOLEAN
);

CREATE TABLE cars (
    id INTEGER PRIMARY KEY,
    make TEXT,
    model TEXT,
    cost DECIMAL
);

CREATE TABLE people_cars (
    person_id INTEGER,
    car_id INTEGER,
    FOREIGN KEY (person_id) REFERENCES people(id),
    FOREIGN KEY (car_id) REFERENCES cars(id),
    PRIMARY KEY (person_id, car_id)
);

INSERT INTO people (id, name, age, has_license) VALUES (1, 'John', 25, TRUE);
INSERT INTO people (id, name, age, has_license) VALUES (2, 'Jane', 30, FALSE);
INSERT INTO people (id, name, age, has_license) VALUES (3, 'Bob', 40, TRUE);

INSERT INTO cars (id, make, model, cost) VALUES (1, 'Toyota', 'Corolla', 15000.00);
INSERT INTO cars (id, make, model, cost) VALUES (2, 'Honda', 'Civic', 18000.00);
INSERT INTO cars (id, make, model, cost) VALUES (3, 'Ford', 'Mustang', 30000.00);


INSERT INTO people_cars (person_id, car_id) VALUES (1, 1);
INSERT INTO people_cars (person_id, car_id) VALUES (1, 2);
INSERT INTO people_cars (person_id, car_id) VALUES (2, 2);
INSERT INTO people_cars (person_id, car_id) VALUES (3, 3);




