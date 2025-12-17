CREATE TABLE IF NOT EXISTS shop_product_variant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    shop_product_id bigint DEFAULT NULL,
    option_name VARCHAR(255) NOT NULL,
    additional_price INTEGER,
    display VARCHAR(255),
    selling VARCHAR(255),
    display_sold_out VARCHAR(255),
    quantity INTEGER,
    last_updated_date DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    created_date DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);