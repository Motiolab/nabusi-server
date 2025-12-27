-- Add shop_cart_id to shop_order table
ALTER TABLE shop_order ADD COLUMN shop_cart_id BIGINT AFTER reward_point;
