INSERT INTO MPA_RATING (RATING_ID, RATING_NAME)
VALUES (1, 'G'),
       (2, 'PG'),
       (3, 'PG-13'),
       (4, 'R'),
       (5, 'NC-17');

INSERT INTO GENRES (GENRE_ID, GENRE_NAME)
VALUES (1, 'Комедия'),
       (2, 'Драма'),
       (3, 'Мультфильм'),
       (4, 'Триллер'),
       (5, 'Документальный'),
       (6, 'Боевик');

INSERT INTO FILMS (NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATING_ID)
VALUES ('film1', 'description1', '1999-09-09', 180, 1),
       ('film2', 'description2', '2025-01-25', 90, 5);

INSERT INTO USERS (EMAIL, LOGIN, NAME, BIRTHDAY)
VALUES ('aaa@a.ru', 'user1', 'user', '2001-01-01'),
       ('bbb@b.ru', 'user2', 'user', '2022-02-02'),
       ('ccc@a.ru', 'user3', 'user', '1995-07-29'),
       ('bbb@b.ru', 'user2', 'user', '1984-11-11');

INSERT INTO FRIENDSHIP
VALUES (2, 1),
       (2, 3),
       (2, 4),
       (3, 1),
       (3,4);