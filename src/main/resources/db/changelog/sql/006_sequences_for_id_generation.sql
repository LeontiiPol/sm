CREATE SEQUENCE property_sequence
INCREMENT BY 5;

CREATE TABLE table_generator
(
    generator_name VARCHAR(255) PRIMARY KEY,
    generated_id   INTEGER
);