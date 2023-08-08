create table products(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    price INT,
    image_url VARCHAR(255)
);

select * from products;

INSERT INTO products VALUES
    (null, '닭가슴살', 1200, 'acbasdfsdf'),
    (null, '간편식', 5000, 'agdff'),
    (null, '맛난거', 6000, 'a');

drop table products;

create table item(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    description VARCHAR(255)
);

INSERT INTO item VALUES
    (null, '닭가슴살', 1200),
    (null, '간편식', 5000),
    (null, '맛난거', 6000);

select * from item;

drop table items;