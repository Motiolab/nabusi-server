CREATE TABLE IF NOT EXISTS `wellness_class` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `center_id` bigint DEFAULT NULL,
  `max_reservation_cnt` int DEFAULT NULL,
  `register_id` bigint DEFAULT NULL,
  `room` varchar(255) DEFAULT NULL,
  `class_image_url_list` varchar(2000) DEFAULT NULL,
  `teacher_id` bigint DEFAULT NULL,
  `wellness_lecture_type_id` bigint DEFAULT NULL,
  `wellness_ticket_management_id_list` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `last_updated_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `is_delete` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_name_center_id` (`name`, `center_id`)
);