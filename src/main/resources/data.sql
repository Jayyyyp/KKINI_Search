-- 대분류(KKINI:1, KKINI GREEN:2)
INSERT INTO Category(category_id, category_name) VALUES
                    (1, 'KKINI'),
                    (2, 'KKINI Green');

-- KKINI와 KKINI GREEN의 소분류
INSERT INTO Category(category_name, parent_id) VALUES
                    ('음식', 1),
                    ('맛없는거', 2),
                    ('풀', 2),
                    ('닭', 1);

INSERT INTO Item (category_id, name, average_rating, product_image, lowest_price) VALUES
                (3, '모짜렐라 비프라쟈냐', 9.6, 'https://sitem.ssgcdn.com/33/93/88/item/1000030889333_i1_290.jpg', 5980),
                (3, '타코야끼', 9.4, 'https://sitem.ssgcdn.com/08/39/22/item/1000526223908_i1_290.jpg', 7980),
                (3, '코다리냉면', 9.38, 'https://sitem.ssgcdn.com/57/73/33/item/1000547337357_i1_290.jpg', 12980),
                (3, '샤브샤브', 9.36, 'https://sitem.ssgcdn.com/13/97/35/item/1000049359713_i1_290.jpg', 9980),
                (3, '물비빔냉면', 8.9, 'https://sitem.ssgcdn.com/29/73/33/item/1000547337329_i1_290.jpg', 12980),
                (3, '고추잡채', 8.88, 'https://sitem.ssgcdn.com/76/02/65/item/1000532650276_i1_290.jpg', 9520),
                (4, '스틱 미숫가루', 9.7, 'https://sitem.ssgcdn.com/89/64/26/item/1000018266489_i1_290.jpg', 9980),
                (4, '친환경 여주쌀', 9.68, 'https://sitem.ssgcdn.com/62/95/66/item/1000019669562_i1_290.jpg', 23800),
                (4, '매실 엑기스', 8.9, 'https://sitem.ssgcdn.com/88/87/58/item/0000008588788_i1_290.jpg', 17000),
                (4, '콩국물 가루', 9.5, 'https://sitem.ssgcdn.com/38/94/78/item/1000028789438_i1_290.jpg', 10480),
                (3, '샤브샤브 밀키트', 5.0, 'https://sitem.ssgcdn.com/38/94/78/item/1000028789438_i1_290.jpg', 15000),
                (3, '그냥 미숫가루', 8.0, 'https://sitem.ssgcdn.com/38/94/78/item/1000028789438_i1_290.jpg', 5000),
                (3, '광양식 소불고기', 7.0, 'https://sitem.ssgcdn.com/30/29/47/item/1000052472930_i1_290.jpg', 7900),
                (3, '쪽파강회', 6.0, 'https://sitem.ssgcdn.com/38/51/65/item/1000043655138_i1_290.jpg', 5900),
                (6, '건강한 닭가슴살', 4.8, 'https://sitem.ssgcdn.com/31/19/40/item/1000084401931_i1_1100.jpg', 4480);


