CREATE TABLE IF NOT EXISTS `teacher` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `introduce` varchar(2000) DEFAULT NULL,
  `career` varchar(2000) DEFAULT NULL,
  `center_id` bigint DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  `use_nick_name` boolean DEFAULT false,
  `image_url` varchar(2000) DEFAULT NULL,
  `is_delete` boolean DEFAULT false,
  `created_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `last_updated_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`)
);