DROP TABLE IF EXISTS Item;
DROP TABLE IF EXISTS Category;

CREATE TABLE `Category`
(
    `category_id` bigint PRIMARY KEY AUTO_INCREMENT,
    `parent_id` bigint, -- 부모 카테고리를 참조하기 위한 컬럼
    `category_name` VARCHAR(255) NOT NULL,
    FOREIGN KEY (`parent_id`) REFERENCES `Category`(`category_id`) -- 자기 참조 설정
);

CREATE TABLE `Item`
(
    `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `category_id` bigint NOT NULL,
    `name` VARCHAR(255),
    `average_rating` DECIMAL(3,2),
    `product_image` VARCHAR(255),
    `lowest_price` bigint,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 생성시간
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 수정시간
    FOREIGN KEY (`category_id`) REFERENCES `Category`(`category_id`)
);
