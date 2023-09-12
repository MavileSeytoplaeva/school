-- liquibase formatted sql

-- changeset mavile:1
CREATE INDEX student_name_index ON student (name);

CREATE INDEX faculty_name_index ON faculty (name);

CREATE INDEX faculty_color_index ON faculty (color);