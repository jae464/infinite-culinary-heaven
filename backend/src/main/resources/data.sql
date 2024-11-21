INSERT INTO topic_ingredient (name, image) VALUES ('두부', 'tofu_sample.jpeg');
INSERT INTO topic_ingredient (name, image) VALUES ('토마토', 'tomato_sample.jpeg');
INSERT INTO topic_ingredient (name, image) VALUES ('감자', 'potato_sample.jpg');

INSERT INTO contest (description, start_date, end_date, topic_ingredient_id)
VALUES
    ('두부 요리 대회', '2024-10-28T00:00:00', '2024-11-03T23:59:59', 1);

INSERT INTO contest (description, start_date, end_date, topic_ingredient_id)
VALUES
    ('토마토 요리 대회', '2024-11-4T00:00:00', '2024-11-10T23:59:59', 2);

INSERT INTO contest (description, start_date, end_date, topic_ingredient_id)
VALUES
    ('감자 요리 대회', '2024-11-11T00:00:00', '2024-11-17T23:59:59', 3);
