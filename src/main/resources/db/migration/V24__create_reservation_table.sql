CREATE TABLE IF NOT EXISTS `reservation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `center_id` bigint DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  `payment_id` bigint DEFAULT NULL,
  `action_member_id` bigint DEFAULT NULL,
  `status` enum('INAPP_RESERVATION', 'ADMIN_RESERVATION', 'ONSITE_RESERVATION','MEMBER_CANCELED_RESERVATION','ADMIN_CANCELED_RESERVATION','CHECK_IN', 'ABSENT') DEFAULT NULL,
  `wellness_lecture_id` bigint DEFAULT NULL,
  `wellness_ticket_issuance_id` bigint DEFAULT NULL,
  `created_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `last_updated_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`)
)