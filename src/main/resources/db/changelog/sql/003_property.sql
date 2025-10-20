CREATE TABLE training.property
(
    property_id bigint PRIMARY KEY,
    name varchar(255)
);

CREATE TABLE training.property_user
(
    property_user_id bigint PRIMARY KEY,
    property_id bigint,
    user_id bigint
);

ALTER TABLE training.property_user ADD CONSTRAINT property_user_user_fk FOREIGN KEY (user_id)
    REFERENCES training.user (user_id) MATCH FULL
    ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE training.property_user ADD CONSTRAINT property_user_property_fk FOREIGN KEY (property_id)
    REFERENCES training.property (property_id) MATCH FULL
    ON DELETE RESTRICT ON UPDATE RESTRICT;

INSERT INTO training.property (property_id, name)
VALUES (1, 'first home'),
       (2, 'second home'),
       (3, 'first car'),
       (4, 'second car');

INSERT INTO training.property_user (property_user_id, property_id, user_id)
VALUES (1, 1, 1),
       (2, 2, 1),
       (3, 3, 1),
       (5, 4, 1);