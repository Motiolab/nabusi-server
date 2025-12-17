CREATE TABLE IF NOT EXISTS `shop_product_review` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(300) DEFAULT NULL,
  `is_private` bit(1) DEFAULT NULL,
  `rating` int DEFAULT NULL,
  `shop_product_id` bigint DEFAULT NULL,
  `shop_product_variant_id` bigint DEFAULT NULL,
  `shop_order_id` bigint DEFAULT NULL,
  `image_url_list` varchar(2000) DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  `is_delete` bit(1) DEFAULT NULL,
  `is_best` bit(1) DEFAULT NULL,
  `idx` int DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_updated_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;