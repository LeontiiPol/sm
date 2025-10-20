INSERT INTO training.user(user_id, username, email)
VALUES (1, 'Leo', 'leo@mail.ru');

INSERT INTO training.post(post_id, content, user_id)
VALUES (1, 'hello', 1),
       (2, 'from', 1),
       (3, 'post', 1),
       (4, '!!!', 1);