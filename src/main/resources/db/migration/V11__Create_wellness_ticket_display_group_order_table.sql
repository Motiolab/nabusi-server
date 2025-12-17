CREATE TABLE IF NOT EXISTS `wellness_ticket_display_group_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `last_updated_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `order_index` int DEFAULT NULL,
  `wellness_ticket_display_group_id` bigint DEFAULT NULL,
  `wellness_ticket_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_wellness_ticket_display_group_id_order_index` (`wellness_ticket_display_group_id`, `order_index`)
);