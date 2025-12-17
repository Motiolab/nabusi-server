CREATE TABLE IF NOT EXISTS `member_memo` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `content` varchar(255) DEFAULT NULL,
    `member_id` bigint DEFAULT NULL,
    `register_id` bigint DEFAULT NULL,
    `created_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    `last_updated_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`)
  );