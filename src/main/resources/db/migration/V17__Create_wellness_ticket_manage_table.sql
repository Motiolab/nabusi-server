CREATE TABLE IF NOT EXISTS `wellness_ticket_management` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `center_id` bigint DEFAULT NULL,
  `wellness_ticket_id` bigint DEFAULT NULL,
  `wellness_ticket_issuance_name` varchar(255) DEFAULT NULL,
  `wellness_ticket_issuance_id_list` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `last_updated_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_wellness_ticket_issuance_name_center_id` (`wellness_ticket_issuance_name`, `center_id`)
);