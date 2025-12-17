-- 역할 테이블 생성 (role)
CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `description` varchar(255) DEFAULT NULL,
  `last_updated_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `name` varchar(255) DEFAULT NULL,
  `center_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- URL 패턴 테이블 생성 (url_pattern)
CREATE TABLE IF NOT EXISTS `url_pattern` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `action_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `last_updated_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `method` varchar(10) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `center_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- 멤버 테이블 생성 (member)
CREATE TABLE IF NOT EXISTS `member` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `last_updated_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `social_name` varchar(255) DEFAULT NULL,
  `center_id_list` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_mobile_social_name` (`mobile`, `social_name`)
);

-- 멤버 역할 테이블 생성 (member_role) 
CREATE TABLE IF NOT EXISTS `member_role` (
  `member_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  KEY `FKdiix07v86r3ntrbs3l02qr7y0` (`role_id`),
  KEY `FK34g7epqlcxqloewku3aoqhhmg` (`member_id`),
  CONSTRAINT `FK34g7epqlcxqloewku3aoqhhmg` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  CONSTRAINT `FKdiix07v86r3ntrbs3l02qr7y0` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
);

-- 역할 URL 패턴 테이블 생성 (role_url_pattern)
CREATE TABLE IF NOT EXISTS `role_url_pattern` (
  `role_id` bigint NOT NULL,
  `url_pattern_id` bigint NOT NULL,
  KEY `FKhxouchu8i5hkkoiijmkpxx0mb` (`url_pattern_id`),
  KEY `FK5dth01hv5us30656nq7k0hdnb` (`role_id`),
  CONSTRAINT `FK5dth01hv5us30656nq7k0hdnb` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKhxouchu8i5hkkoiijmkpxx0mb` FOREIGN KEY (`url_pattern_id`) REFERENCES `url_pattern` (`id`)
);

-- 센터 테이블 생성 (center)
CREATE TABLE IF NOT EXISTS `center` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id_list` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `address` varchar(255) NOT NULL,
  `code` varchar(50) NOT NULL,
  `detail_address` varchar(255) NOT NULL,
  `road_name` varchar(100) NOT NULL,
  `is_active` TINYINT(1) NOT NULL,
  `image_url_list` varchar(2000) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `created_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `last_updated_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_road_name_name` (`road_name`,`name`)
);