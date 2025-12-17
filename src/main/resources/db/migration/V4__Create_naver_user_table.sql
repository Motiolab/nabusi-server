CREATE TABLE IF NOT EXISTS `naver_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `birthday` varchar(255) DEFAULT NULL,
  `birthyear` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `email` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `last_updated_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `mobile` varchar(255) DEFAULT NULL,
  `mobile_e164` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_mobile_e164` (`mobile_e164`)
)