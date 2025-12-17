CREATE TABLE IF NOT EXISTS `wellness_ticket_issuance_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `action_member_id` bigint DEFAULT NULL,
  `changed_cnt` int DEFAULT NULL,
  `wellness_ticket_issuance_id` bigint DEFAULT NULL,
  `reason` enum('ADMIN_CANCELED_RESERVATION','ADMIN_MODIFY_CNT','ADMIN_RESERVATION','ADMIN_ISSUED','ADMIN_DELETE_RESERVATION','MEMBER_CANCELED_RESERVATION','MEMBER_RESERVATION','MEMBER_ISSUED') DEFAULT NULL,
  `reservation_id` bigint DEFAULT NULL,
  `wellness_lecture_id` bigint DEFAULT NULL,
  `created_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `last_updated_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`)
);