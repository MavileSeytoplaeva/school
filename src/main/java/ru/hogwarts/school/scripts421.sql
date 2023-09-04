ALTER TABLE Student ADD CONSTRAINT age_constraint CHECK (age >= 16);

ALTER TABLE Student ADD CONSTRAINT name_unique_constraint UNIQUE (name)

ALTER TABLE Faculty ADD CONSTRAINT name_color_unique_constraint UNIQUE (name, color);

ALTER TABLE Student ALTER COLUMN age SET DEFAULT 20;
