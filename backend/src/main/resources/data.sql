INSERT INTO users (created_at, modified_at, username, oauth_id, oauth_type)
VALUES ('2024-11-20T11:11:11', '2024-11-20T11:11:11', '이민재', '1', 'kakao');

INSERT INTO topic_ingredient (name, image)
VALUES ('두부', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/tofu_sample.jpeg');
INSERT INTO topic_ingredient (name, image)
VALUES ('토마토', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/tomato_sample.jpeg');
INSERT INTO topic_ingredient (name, image)
VALUES ('감자', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/potato_sample.jpg');
INSERT INTO topic_ingredient (name, image)
VALUES ('계란', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/egg_sample.jpg');
INSERT INTO topic_ingredient (name, image)
VALUES ('오징어', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/squid_sample.jpeg');
INSERT INTO topic_ingredient (name, image)
VALUES ('연어', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/salmon_sample.jpg');
INSERT INTO topic_ingredient (name, image)
VALUES ('당근', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/carrot_sample.jpg');
INSERT INTO topic_ingredient (name, image)
VALUES ('새우', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/shrimp_sample.jpg');
INSERT INTO topic_ingredient (name, image)
VALUES ('닭고기', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/chicken_sample.jpg');
INSERT INTO topic_ingredient (name, image)
VALUES ('돼지고기', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/pork_sample.jpg');
INSERT INTO topic_ingredient (name, image)
VALUES ('참치', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/tuna_sample.jpg');

INSERT INTO contest (name, description, start_date, end_date, topic_ingredient_id)
VALUES ('제1회 대회', '두부 요리 대회', '2024-11-04T00:00:00', '2024-11-10T23:59:59', 1);

INSERT INTO contest (name, description, start_date, end_date, topic_ingredient_id)
VALUES ('제2회 대회', '토마토 요리 대회', '2024-11-11T00:00:00', '2024-11-17T23:59:59', 2);

INSERT INTO contest (name, description, start_date, end_date, topic_ingredient_id)
VALUES ('제3회 대회', '감자 요리 대회', '2024-11-18T00:00:00', '2024-11-24T23:59:59', 3);

INSERT INTO contest (name, description, start_date, end_date, topic_ingredient_id)
VALUES ('제4회 대회', '계란 요리 대회', '2024-11-25T00:00:00', '2024-12-01T23:59:59', 4);

INSERT INTO contest (name, description, start_date, end_date, topic_ingredient_id)
VALUES ('제5회 대회', '오징어 요리 대회', '2024-12-02T00:00:00', '2024-12-08T23:59:59', 5);

INSERT INTO contest (name, description, start_date, end_date, topic_ingredient_id)
VALUES ('제6회 연어 요리 대회',
        '이번주 주재료는 연어입니다. 자기만의 창의적인 요리를 뽐내봐요.',
        '2024-12-09T00:00:00', '2024-12-15T23:59:59', 6);

INSERT INTO contest (name, description, start_date, end_date, topic_ingredient_id)
VALUES ('제7회 당근 요리 대회', '이번 주 주재료는 당근입니다. 다양하고 창의적인 당근 요리를 기대합니다.',
        '2024-12-16T00:00:00', '2024-12-22T23:59:59', 7);

INSERT INTO contest (name, description, start_date, end_date, topic_ingredient_id)
VALUES ('제8회 새우 요리 대회', '이번 주 주재료는 새우입니다.',
        '2024-12-23T00:00:00', '2024-12-29T23:59:59', 8);

INSERT INTO contest (name, description, start_date, end_date, topic_ingredient_id)
VALUES ('제9회 닭고기 요리 대회', '이번 주 주재료는 닭고기입니다.',
        '2024-12-30T00:00:00', '2025-01-05T23:59:59', 9);

INSERT INTO contest (name, description, start_date, end_date, topic_ingredient_id)
VALUES ('제10회 돼지고기 요리 대회', '이번 주 주재료는 돼지고기입니다.',
        '2025-01-06T00:00:00', '2025-01-12T23:59:59', 10);

INSERT INTO contest (name, description, start_date, end_date, topic_ingredient_id)
VALUES ('제11회 참치 요리 대회', '이번 주 주재료는 참치입니다.',
        '2025-01-13T00:00:00', '2025-01-19T23:59:59', 11);

INSERT INTO contest (name, description, start_date, end_date, topic_ingredient_id)
VALUES ('제12회 대회', '이번 주 주재료는 두부입니다.', '2025-01-20T00:00:00', '2025-01-26T23:59:59', 1);

INSERT INTO contest (name, description, start_date, end_date, topic_ingredient_id)
VALUES ('제13회 대회', '이번 주 주재료는 토마토입니다.', '2025-01-27T00:00:00', '2025-02-02T23:59:59', 2);

-- 감자 대회 레시피
INSERT INTO recipe (created_at, modified_at, title, description, thumbnail_image, user_id, contest_id)
VALUES ('2024-11-22T11:11:11', '2024-11-22T11:11:11', '휴게소 알감자', '휴게소에서 파는 알감자의 맛을 그대로 재현해봤습니다.',
        'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/recipe_2_sample.jpg', 1, 3);

INSERT INTO ingredient (name, quantity, recipe_id)
VALUES ('감자', '50g', 1);
INSERT INTO ingredient (name, quantity, recipe_id)
VALUES ('소금', '적당히', 1);

INSERT INTO step (step, description, image, recipe_id)
VALUES (1, '먼저 감자를 삶습니다.', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_sample.jpg', 1);
INSERT INTO step (step, description, image, recipe_id)
VALUES (2, '감자에 양념을 뿌립니다.', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_2_sample.jpg', 1);

--  계란 대회 레시피
INSERT INTO recipe (created_at, modified_at, title, description, thumbnail_image, user_id, contest_id)
VALUES ('2024-11-25T11:11:11', '2024-11-25T11:11:11', '들기름 계란 후라이', '간단하고 맛있는 들기름 계란후라이',
        'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/recipe_egg_sample.jpg', 1, 4);

INSERT INTO ingredient (name, quantity, recipe_id)
VALUES ('계란', '3개', 2);
INSERT INTO ingredient (name, quantity, recipe_id)
VALUES ('소금', '적당히', 2);

INSERT INTO step (step, description, image, recipe_id)
VALUES (1, '먼저 감자를 삶습니다.', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_sample.jpg', 2);
INSERT INTO step (step, description, image, recipe_id)
VALUES (2, '감자에 양념을 뿌립니다.', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_2_sample.jpg', 2);

-- 오징어 요리 대회
INSERT INTO recipe (created_at, modified_at, title, description, thumbnail_image, user_id, contest_id)
VALUES ('2024-12-02T11:11:11', '2024-12-02T11:11:11', '오징어 볶음', '간단하고 맛있는 오징어 볶음',
        'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/recipe_squid_sample.jpg', 1, 5);

INSERT INTO step (step, description, image, recipe_id)
VALUES (1, '먼저 감자를 삶습니다.', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_squid_1_sample.jpg', 3);
INSERT INTO step (step, description, image, recipe_id)
VALUES (2, '오징어를 볶습니다.', 'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/recipe_squid_sample.jpg', 3);

-- 연어 요리 대회 레시피
INSERT INTO recipe (created_at, modified_at, title, description, thumbnail_image, user_id, contest_id)
VALUES ('2024-12-09T00:00:01', '2024-12-09T00:00:01', '허니 연어 스테이크', '달콤한 허니 글레이즈로 만든 연어 스테이크입니다.',
        'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/recipe_salmon_sample.jpg', 1, 6);

INSERT INTO ingredient (name, quantity, recipe_id)
VALUES ('연어', '200g', 4);
INSERT INTO ingredient (name, quantity, recipe_id)
VALUES ('꿀', '2 큰술', 4);
INSERT INTO ingredient (name, quantity, recipe_id)
VALUES ('버터', '1 큰술', 4);
INSERT INTO ingredient (name, quantity, recipe_id)
VALUES ('간장', '1 큰술', 4);
INSERT INTO ingredient (name, quantity, recipe_id)
VALUES ('마늘', '다진 것 1 큰술', 4);

INSERT INTO step (step, description, image, recipe_id)
VALUES (1, '팬에 버터를 녹이고 다진 마늘을 볶습니다.',
        'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_salmon_1_sample.jpeg', 4);
INSERT INTO step (step, description, image, recipe_id)
VALUES (2, '연어를 넣고 한 면씩 노릇하게 구워줍니다.',
        'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_salmon_2_sample.jpeg', 4);
INSERT INTO step (step, description, image, recipe_id)
VALUES (3, '꿀, 간장을 섞어 만든 소스를 팬에 추가하고 연어에 글레이즈를 입힙니다.',
        'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_salmon_3_sample.png', 4);
INSERT INTO step (step, description, image, recipe_id)
VALUES (4, '접시에 연어를 담고 소스를 뿌린 후 서빙합니다.',
        'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/recipe_salmon_sample.jpg', 4);

-- 새우 요리 대회 레시피
INSERT INTO recipe (created_at, modified_at, title, description, thumbnail_image, user_id, contest_id)
VALUES ('2024-12-23T00:00:01', '2024-12-23T00:00:01', '새우 치즈 버터 구이', '호불호 없는 음식 새우 치즈 버터 구이입니다.',
        'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/recipe_shrimp_sample.jpg', 1, 8);

INSERT INTO ingredient (name, quantity, recipe_id)
VALUES ('대하', '500g', 5);
INSERT INTO ingredient (name, quantity, recipe_id)
VALUES ('청주', '2 큰술', 5);
INSERT INTO ingredient (name, quantity, recipe_id)
VALUES ('피자치즈', '40g', 5);
INSERT INTO ingredient (name, quantity, recipe_id)
VALUES ('마요네즈', '4 큰술', 5);
INSERT INTO ingredient (name, quantity, recipe_id)
VALUES ('후추', '약간', 5);

INSERT INTO step (step, description, image, recipe_id)
VALUES (1, '대하의 수염과 다리를 잘라 손질해 주세요. 등을 따라 껍데기에 가위집을 내고 칼집을 넣어 반으로 잘라 주세요.',
        'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_shrimp_1.jpg', 5);
INSERT INTO step (step, description, image, recipe_id)
VALUES (2, '손질한 대하에 청주 2큰술과 후추 약간을 넣고 밑간을해주세요. 밑간한 대하는 키친타월에 올려 물기를 제거해 주세요.',
        'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_shrimp_2.jpg', 5);
INSERT INTO step (step, description, image, recipe_id)
VALUES (3, '볼에 피자치즈, 마요네즈, 다진 마늘, 후춧가루를 넣고 섞은 후 짤 주머니에 넣어 준비해 주세요.',
        'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_shrimp_3.jpg', 5);
INSERT INTO step (step, description, image, recipe_id)
VALUES (4, '법랑 접시에 대하를 동그랗게 펼친 후 만들어둔 소스를 짜서 올려주세요.',
        'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_shrimp_4.jpg', 5);
INSERT INTO step (step, description, image, recipe_id)
VALUES (5, '법랑 접시를 3단에 넣은 후 광파오븐 수동 요리 <구이>에서 17분간 구워주세요.',
        'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_shrimp_5.jpg', 5);
INSERT INTO step (step, description, image, recipe_id)
VALUES (6, '완성된 대하 치즈구이를 접시에 담아 레몬, 파슬리 등을 올려 맛있게 즐겨주세요.',
        'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_shrimp_6.jpg', 5);

-- 두부 요리 대회 레시피
INSERT INTO recipe (created_at, modified_at, title, description, thumbnail_image, user_id, contest_id)
VALUES ('2025-01-20T00:00:01', '2025-01-20T00:00:01', '간장두부조림', '근본 두부요리',
        'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/recipe_tofu_sample.jpg', 1, 12);

INSERT INTO ingredient (name, quantity, recipe_id)
VALUES ('두부', '1모', 6);
INSERT INTO ingredient (name, quantity, recipe_id)
VALUES ('식용유', '2 큰술', 6);
INSERT INTO ingredient (name, quantity, recipe_id)
VALUES ('대파', '약간', 6);
INSERT INTO ingredient (name, quantity, recipe_id)
VALUES ('통깨', '약간', 6);
INSERT INTO ingredient (name, quantity, recipe_id)
VALUES ('간장', '2큰술', 6);
INSERT INTO ingredient (name, quantity, recipe_id)
VALUES ('물', '4큰술', 6);
INSERT INTO ingredient (name, quantity, recipe_id)
VALUES ('설탕', '1/2큰술', 6);

INSERT INTO step (step, description, image, recipe_id)
VALUES (1, '두부는 도톰한 두께로 썰고 키친타월로 물기를 제거해 주세요. 양념재료를 섞어 준비해 주세요.',
        'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_tofu_1.jpg', 6);
INSERT INTO step (step, description, image, recipe_id)
VALUES (2, '달군 팬에 기름을 두르고 두부를 앞뒤로 노릇하게 구워주세요. ',
        'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_tofu_2.jpg', 6);
INSERT INTO step (step, description, image, recipe_id)
VALUES (3, '구운 두부에 양념재료를 넣고 약불에서 5분 정도 졸여주세요. ',
        'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_tofu_3.jpg', 6);
INSERT INTO step (step, description, image, recipe_id)
VALUES (4, '완성된 간장두부조림을 접시에 담고 송송 썬 대파와 통깨를 뿌려 밥과 함께 즐겨주세요.',
        'https://culinary-heaven.s3.ap-northeast-2.amazonaws.com/image/step_tofu_4.jpg', 6);

