CREATE TABLE `admin_user` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `email` VARCHAR(255) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `birth_year` VARCHAR(10),
    `birth_day` VARCHAR(10),
    `gender` VARCHAR(10),
    `phone_number` VARCHAR(20),
    `member_id` BIGINT NOT NULL,
    `created_date` DATETIME NOT NULL,
    `last_updated_date` DATETIME NOT NULL,
    CONSTRAINT `fk_admin_user_member` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
