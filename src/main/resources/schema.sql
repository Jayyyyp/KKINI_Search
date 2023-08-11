DROP TABLE IF EXISTS Item;
DROP TABLE IF EXISTS Category;

CREATE TABLE `Category`
(
    `category_id` bigint PRIMARY KEY,
    `category_name` VARCHAR(255) NOT NULL
);

CREATE TABLE `Item`
(
    `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `category_id` bigint NOT NULL,
    `name` VARCHAR(255),
    `average_rating` DECIMAL(3,2),
    `product_image` VARCHAR(255),
    `lowest_price` bigint,
    `created_at` TIMESTAMP,
    `updated_at` TIMESTAMP,
    FOREIGN KEY (`category_id`) REFERENCES `Category`(`category_id`)
);
