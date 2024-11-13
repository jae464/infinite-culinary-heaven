INSERT INTO topic_ingredient (name, image) VALUES ('두부', 'file:potato.jpg');
INSERT INTO topic_ingredient (name, image) VALUES ('토마토', 'file:potato.jpg');
INSERT INTO topic_ingredient (name, image) VALUES ('오징어', 'file:potato.jpg');
INSERT INTO topic_ingredient (name, image) VALUES ('양파', 'file:potato.jpg');

INSERT INTO contest (description, start_date, end_date, topic_ingredient_id)
VALUES
    ('두부 요리 대회', '2024-10-28T00:00:00', '2024-11-03T23:59:59', 1);

INSERT INTO contest (description, start_date, end_date, topic_ingredient_id)
VALUES
    ('토마토 요리 대회', '2024-11-4T00:00:00', '2024-11-10T23:59:59', 2);
