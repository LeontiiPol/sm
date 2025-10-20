CREATE TABLE training.user
(
    user_id bigint PRIMARY KEY,
    username varchar(255),
    email varchar(255)
);

CREATE TABLE training.post
(
    post_id bigint PRIMARY KEY,
    content varchar(255),
    user_id bigint
);

ALTER TABLE training.post ADD CONSTRAINT post_user_fk FOREIGN KEY (user_id)
    REFERENCES training.user (user_id) MATCH FULL
    ON DELETE RESTRICT ON UPDATE RESTRICT;