CREATE TABLE IF NOT EXISTS naver_token (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    access_token varchar(255) DEFAULT NULL,
    expires_at varchar(255) DEFAULT NULL,
    issued_at datetime(6) DEFAULT NULL,
    refresh_token varchar(255) DEFAULT NULL,
    member_id bigint DEFAULT NULL,
    last_updated_date DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    created_date DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);