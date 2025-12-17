CREATE TABLE IF NOT EXISTS `wellness_ticket_extension` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `last_updated_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `extend_date` int DEFAULT NULL,
  `is_apply_after` bit(1) DEFAULT NULL,
  `register_id` bigint DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `target_date` datetime(6) DEFAULT NULL,
  `wellness_ticket_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
);