CREATE TABLE IF NOT EXISTS shop_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    payment_id BIGINT,
    purchase_confirmation BOOLEAN NOT NULL DEFAULT FALSE,
    status VARCHAR(255) NOT NULL,
    total_price INTEGER NOT NULL,
    total_discount_price INTEGER NOT NULL DEFAULT 0,
    total_additional_price INTEGER NOT NULL DEFAULT 0,
    receiver_name VARCHAR(255) NOT NULL,
    receiver_phone VARCHAR(255) NOT NULL,
    receiver_address VARCHAR(255) NOT NULL,
    receiver_address_code VARCHAR(255) NOT NULL,
    receiver_detail_address VARCHAR(255) NOT NULL,
    used_point BIGINT,
    reward_point BIGINT,
    last_updated_date DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    created_date DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);