CREATE TABLE IF NOT EXISTS `on_site_pay` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `payment_id` bigint NOT NULL,
  `discount_rate` int DEFAULT NULL,
  `total_pay_value` int DEFAULT NULL,
  `unpaid_value` int DEFAULT NULL,
  `card_installment` int DEFAULT NULL,
  `card_pay_value` int DEFAULT NULL,
  `cash_pay_value` int DEFAULT NULL,
  `payer_member_id` bigint DEFAULT NULL,
  `payee_member_id` bigint DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `payment_date` datetime(6) DEFAULT NULL,
  `payment_method` enum('IN_APP','ON_SITE') DEFAULT NULL,
  `update_member_id` bigint DEFAULT NULL,
  `created_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `last_updated_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`)
);