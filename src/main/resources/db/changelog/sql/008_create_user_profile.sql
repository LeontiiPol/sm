CREATE TABLE user_profile
(
    user_profile_id BIGINT PRIMARY KEY,
    login           VARCHAR(255),
    user_id         BIGINT
);

ALTER TABLE training.user_profile
    ADD CONSTRAINT user_profile_user_fk FOREIGN KEY (user_id)
        REFERENCES training.users (user_id) MATCH FULL
        ON DELETE RESTRICT ON UPDATE RESTRICT;

CREATE SEQUENCE user_profile_id_seq
INCREMENT BY 50
MINVALUE 1;