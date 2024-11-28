INSERT INTO users (created_at, modified_at, username, oauth_id, oauth_type)
VALUES ('2024-11-20T11:11:11','2024-11-20T11:11:11','이민재', '1','kakao');

INSERT INTO topic_ingredient (name, image) VALUES ('두부', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/tofu_sample.jpeg');
INSERT INTO topic_ingredient (name, image) VALUES ('토마토', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/tomato_sample.jpeg');
INSERT INTO topic_ingredient (name, image) VALUES ('감자', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/potato_sample.jpg');
INSERT INTO topic_ingredient (name, image) VALUES ('계란', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/egg_sample.jpg');

INSERT INTO contest (description, start_date, end_date, topic_ingredient_id)
VALUES
    ('두부 요리 대회', '2024-11-04T00:00:00', '2024-11-10T23:59:59', 1);

INSERT INTO contest (description, start_date, end_date, topic_ingredient_id)
VALUES
    ('토마토 요리 대회', '2024-11-11T00:00:00', '2024-11-17T23:59:59', 2);

INSERT INTO contest (description, start_date, end_date, topic_ingredient_id)
VALUES
    ('감자 요리 대회', '2024-11-18T00:00:00', '2024-11-24T23:59:59', 3);

INSERT INTO contest (description, start_date, end_date, topic_ingredient_id)
VALUES
    ('계란 요리 대회', '2024-11-25T00:00:00', '2024-12-01T23:59:59', 4);


INSERT INTO recipe (created_at, modified_at, title, description, thumbnail_image, user_id, contest_id, competition_count, win_count)
VALUES ('2024-11-22T11:11:11','2024-11-22T11:11:11','휴게소 알감자', '휴게소에서 파는 알감자의 맛을 그대로 재현해봤습니다.','https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/recipe_2_sample.jpg', 1, 3, 0, 0);

INSERT INTO ingredient (name, quantity, recipe_id) VALUES ('감자', '50g', 1);
INSERT INTO ingredient (name, quantity, recipe_id) VALUES ('소금', '적당히', 1);

INSERT INTO step (step, description, image, recipe_id) VALUES (1, '먼저 감자를 삶습니다.', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_sample.jpg', 1);
INSERT INTO step (step, description, image, recipe_id) VALUES (2, '감자에 양념을 뿌립니다.', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_2_sample.jpg', 1);

INSERT INTO recipe (created_at, modified_at, title, description, thumbnail_image, user_id, contest_id, competition_count, win_count)
VALUES ('2024-11-25T11:11:11','2024-11-25T11:11:11','들기름 계란 후라이', '간단하고 맛있는 들기름 계란후라이','https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/recipe_egg_sample.jpg',1, 4, 0, 0);

INSERT INTO ingredient (name, quantity, recipe_id) VALUES ('계란', '3개', 2);
INSERT INTO ingredient (name, quantity, recipe_id) VALUES ('소금', '적당히', 2);

INSERT INTO step (step, description, image, recipe_id) VALUES (1, '먼저 감자를 삶습니다.', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_sample.jpg', 2);
INSERT INTO step (step, description, image, recipe_id) VALUES (2, '감자에 양념을 뿌립니다.', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_2_sample.jpg', 2);
