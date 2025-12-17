CREATE TABLE IF NOT EXISTS `invitation_admin_member` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `mobile` varchar(255) DEFAULT NULL,
  `center_id` bigint DEFAULT NULL,
  `is_accept` TINYINT(1) NOT NULL,
  `send_admin_member_id` bigint DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `last_updated_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `created_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`)
);