CREATE TABLE `wellness_lecture_review` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(300) DEFAULT NULL,
  `is_private` bit(1) DEFAULT NULL,
  `rating` int DEFAULT NULL,
  `teacher_id` bigint DEFAULT NULL,
  `wellness_class_id` bigint DEFAULT NULL,
  `wellness_lecture_id` bigint DEFAULT NULL,
  `center_id` bigint DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  `is_delete` bit(1) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_updated_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;