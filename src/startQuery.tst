drop table if exists students;

CREATE TABLE students (
	student_id Serial Primary Key,
	first_name Text Not Null,
	last_name Text Not Null,
	email Text Not Null Unique,
	enrollment_date Date
);

INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES
('John', 'Doe', 'john.doe@example.com', '2023-09-01'),
('Jane', 'Smith', 'jane.smith@example.com', '2023-09-01'),
('Jim', 'Beam', 'jim.beam@example.com', '2023-09-02');