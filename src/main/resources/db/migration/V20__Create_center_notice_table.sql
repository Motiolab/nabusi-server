CREATE TABLE IF NOT EXISTS `center_notice` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `center_id` bigint DEFAULT NULL,
  `register_id` bigint DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `created_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `last_updated_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `is_delete` bit(1) DEFAULT NULL,
  `is_popup` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
)