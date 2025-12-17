CREATE TABLE IF NOT EXISTS `wellness_ticket_display_group` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `center_id` bigint DEFAULT NULL,
  `created_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `is_delete` bit(1) DEFAULT FALSE,
  `is_display` bit(1) DEFAULT NULL,
  `last_updated_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `name` varchar(255) DEFAULT NULL,
  `order_index` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_name` (`name`),
  UNIQUE KEY `UK_order_index` (`order_index`)
);