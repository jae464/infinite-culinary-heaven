INSERT INTO users (created_at, modified_at, username, oauth_id, oauth_type)
VALUES ('2024-11-20T11:11:11','2024-11-20T11:11:11','이민재', '1','kakao');

INSERT INTO topic_ingredient (name, image) VALUES ('두부', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/tofu_sample.jpeg');
INSERT INTO topic_ingredient (name, image) VALUES ('토마토', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/tomato_sample.jpeg');
INSERT INTO topic_ingredient (name, image) VALUES ('감자', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/potato_sample.jpg');
INSERT INTO topic_ingredient (name, image) VALUES ('계란', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/egg_sample.jpg');
INSERT INTO topic_ingredient (name, image) VALUES ('오징어', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/squid_sample.jpeg');
INSERT INTO topic_ingredient (name, image) VALUES ('연어', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/salmon_sample.jpg');

INSERT INTO contest (name, description, start_date, end_date, topic_ingredient_id)
VALUES
    ('제1회 대회','두부 요리 대회', '2024-11-04T00:00:00', '2024-11-10T23:59:59', 1);

INSERT INTO contest (name, description, start_date, end_date, topic_ingredient_id)
VALUES
    ('제2회 대회','토마토 요리 대회', '2024-11-11T00:00:00', '2024-11-17T23:59:59', 2);

INSERT INTO contest (name, description, start_date, end_date, topic_ingredient_id)
VALUES
    ('제4회 대회','감자 요리 대회', '2024-11-18T00:00:00', '2024-11-24T23:59:59', 3);

INSERT INTO contest (name, description, start_date, end_date, topic_ingredient_id)
VALUES
    ('제4회 대회', '계란 요리 대회', '2024-11-25T00:00:00', '2024-12-01T23:59:59', 4);

INSERT INTO contest (name, description, start_date, end_date, topic_ingredient_id)
VALUES
    ('제5회 대회', '오징어 요리 대회', '2024-12-02T00:00:00', '2024-12-08T23:59:59', 5);

INSERT INTO contest (name, description, start_date, end_date, topic_ingredient_id)
VALUES
    ('제6회 대회', '연어 요리 대회', '2024-12-09T00:00:00', '2024-12-15T23:59:59', 6);


-- 감자 대회 레시피
INSERT INTO recipe (created_at, modified_at, title, description, thumbnail_image, user_id, contest_id)
VALUES ('2024-11-22T11:11:11','2024-11-22T11:11:11','휴게소 알감자', '휴게소에서 파는 알감자의 맛을 그대로 재현해봤습니다.','https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/recipe_2_sample.jpg', 1, 3);

INSERT INTO ingredient (name, quantity, recipe_id) VALUES ('감자', '50g', 1);
INSERT INTO ingredient (name, quantity, recipe_id) VALUES ('소금', '적당히', 1);

INSERT INTO step (step, description, image, recipe_id) VALUES (1, '먼저 감자를 삶습니다.', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_sample.jpg', 1);
INSERT INTO step (step, description, image, recipe_id) VALUES (2, '감자에 양념을 뿌립니다.', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_2_sample.jpg', 1);

--  계란 대회 레시피
INSERT INTO recipe (created_at, modified_at, title, description, thumbnail_image, user_id, contest_id)
VALUES ('2024-11-25T11:11:11','2024-11-25T11:11:11','들기름 계란 후라이', '간단하고 맛있는 들기름 계란후라이','https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/recipe_egg_sample.jpg',1, 4);

INSERT INTO ingredient (name, quantity, recipe_id) VALUES ('계란', '3개', 2);
INSERT INTO ingredient (name, quantity, recipe_id) VALUES ('소금', '적당히', 2);

INSERT INTO step (step, description, image, recipe_id) VALUES (1, '먼저 감자를 삶습니다.', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_sample.jpg', 2);
INSERT INTO step (step, description, image, recipe_id) VALUES (2, '감자에 양념을 뿌립니다.', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_2_sample.jpg', 2);

-- 오징어 요리 대회
INSERT INTO recipe (created_at, modified_at, title, description, thumbnail_image, user_id, contest_id)
VALUES ('2024-12-02T11:11:11','2024-12-02T11:11:11','오징어 볶음', '간단하고 맛있는 오징어 볶음','https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/recipe_squid_sample.jpg',1, 5);

INSERT INTO step (step, description, image, recipe_id) VALUES (1, '먼저 감자를 삶습니다.', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_squid_1_sample.jpg', 3);
INSERT INTO step (step, description, image, recipe_id) VALUES (2, '오징어를 볶습니다.', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/recipe_squid_sample.jpg', 3);

-- 연어 요리 대회 레시피
INSERT INTO recipe (created_at, modified_at, title, description, thumbnail_image, user_id, contest_id)
VALUES ('2024-12-09T11:11:11','2024-12-09T11:11:11','허니 연어 스테이크', '달콤한 허니 글레이즈로 만든 연어 스테이크입니다.', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/recipe_salmon_sample.jpg', 1, 6);

INSERT INTO ingredient (name, quantity, recipe_id) VALUES ('연어', '200g', 4);
INSERT INTO ingredient (name, quantity, recipe_id) VALUES ('꿀', '2 큰술', 4);
INSERT INTO ingredient (name, quantity, recipe_id) VALUES ('버터', '1 큰술', 4);
INSERT INTO ingredient (name, quantity, recipe_id) VALUES ('간장', '1 큰술', 4);
INSERT INTO ingredient (name, quantity, recipe_id) VALUES ('마늘', '다진 것 1 큰술', 4);

INSERT INTO step (step, description, image, recipe_id) VALUES (1, '팬에 버터를 녹이고 다진 마늘을 볶습니다.', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_salmon_1_sample.jpeg', 4);
INSERT INTO step (step, description, image, recipe_id) VALUES (2, '연어를 넣고 한 면씩 노릇하게 구워줍니다.', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_salmon_2_sample.jpeg', 4);
INSERT INTO step (step, description, image, recipe_id) VALUES (3, '꿀, 간장을 섞어 만든 소스를 팬에 추가하고 연어에 글레이즈를 입힙니다.', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_salmon_3_sample.png', 4);
INSERT INTO step (step, description, image, recipe_id) VALUES (4, '접시에 연어를 담고 소스를 뿌린 후 서빙합니다.', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/recipe_salmon_sample.jpg', 4);
