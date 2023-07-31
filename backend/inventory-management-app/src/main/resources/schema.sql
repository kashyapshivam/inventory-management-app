CREATE TABLE IF NOT EXISTS ITEM (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    category VARCHAR(100),
    price_per_unit DOUBLE NOT NULL,
    shelf_number VARCHAR(10),
    vendor_link VARCHAR(255)
);
