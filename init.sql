-- Création des bases de données
CREATE DATABASE IF NOT EXISTS products_db;
CREATE DATABASE IF NOT EXISTS orders_db;
CREATE DATABASE IF NOT EXISTS payments_db;

-- Création des tables pour products_db
USE products_db;
CREATE TABLE IF NOT EXISTS product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL
);

-- Création des tables pour orders_db
USE orders_db;
CREATE TABLE IF NOT EXISTS `order` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    status VARCHAR(50) NOT NULL
);

-- Création des tables pour payments_db
USE payments_db;
CREATE TABLE IF NOT EXISTS payment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_status VARCHAR(50) NOT NULL
);

-- Insertion de données de test
USE products_db;
INSERT INTO product (name, price) VALUES ('Laptop', 999.99);
INSERT INTO product (name, price) VALUES ('Smartphone', 599.99);

USE orders_db;
INSERT INTO `order` (product_id, quantity, total_price, status) 
VALUES (1, 2, 1999.98, 'PENDING');

USE payments_db;
INSERT INTO payment (order_id, amount, payment_status) 
VALUES (1, 1999.98, 'COMPLETED');