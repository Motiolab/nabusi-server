CREATE TABLE IF NOT EXISTS `policy_wellness_class` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `center_id` bigint DEFAULT NULL,
  `reservation_start` INT NOT NULL,
  `reservation_end` INT NOT NULL,
  `reservation_cancel_limit` INT NOT NULL,
  `auto_reserve_before_class_time` INT NOT NULL,
  `auto_absent_limit` INT NOT NULL,
  `is_active_auto_reservation` TINYINT(1) NOT NULL,
  `last_updated_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `created_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`)
);