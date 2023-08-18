DROP TABLE IF EXISTS Ratings;
DROP TABLE IF EXISTS Item;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Category;

CREATE TABLE `Category`
(
    `category_id` bigint PRIMARY KEY AUTO_INCREMENT,
    `parent_id` bigint,
    `category_name` VARCHAR(255) NOT NULL,
    FOREIGN KEY (`parent_id`) REFERENCES `Category`(`category_id`)
);

CREATE TABLE `Item`
(
    `item_id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `category_id` bigint NOT NULL,
    `name` VARCHAR(255),
    `average_rating` DECIMAL(3,2),
    `product_image` VARCHAR(255),
    `lowest_price` bigint,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`category_id`) REFERENCES `Category`(`category_id`)
);

-- Users 테이블 추가
CREATE TABLE `Users`
(
    `user_id` bigint PRIMARY KEY AUTO_INCREMENT,
    `user_name` VARCHAR(255) NOT NULL
);

CREATE TABLE `Ratings`
(
    `rating_id` bigint PRIMARY KEY AUTO_INCREMENT,
    `item_id` bigint NOT NULL,
    `user_id` bigint NOT NULL,
    `rating_value` bigint NOT NULL,
    `rating_text` TEXT,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`item_id`) REFERENCES `Item`(`item_id`),
    FOREIGN KEY (`user_id`) REFERENCES `Users`(`user_id`) -- 외래 키 제약 조건 추가
);