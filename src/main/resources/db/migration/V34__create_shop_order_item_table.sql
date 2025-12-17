CREATE TABLE IF NOT EXISTS shop_order_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    shop_order_id BIGINT NOT NULL,
    shop_product_variant_id BIGINT NOT NULL,
    shop_product_id BIGINT NOT NULL,
    option_name VARCHAR(255) NOT NULL,
    additional_price INTEGER NOT NULL DEFAULT 0,
    product_name VARCHAR(255) NOT NULL,
    price INTEGER NOT NULL,
    product_price INTEGER NOT NULL,
    list_image VARCHAR(255),
    detail_image VARCHAR(255),
    quantity INTEGER NOT NULL,
    total_price INTEGER NOT NULL
);