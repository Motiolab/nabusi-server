INSERT INTO `center` (`id`, `address`, `code`, `detail_address`, `name`, `road_name`, `description`, `is_active`, `created_date`, `last_updated_date`)
VALUES
	(1, '서울 강남구 도산대로 311 (신사동)', '9P8L', '6층', 'NABUSI', 'Dosan-daero', '요가 프로그램과 웰니스 푸드를 결합한 복합 웰니스 솔루션을 만나보세요.', true, '2024-11-26 11:28:09', '2024-11-26 11:28:09');

-- Insert roles
INSERT INTO role (name, description, created_date, last_updated_date, center_id)
VALUES
    ('NABUSI_OWNER', 'NABUSI_OWNER ROLE', NOW(), NOW(), 1),
    ('NABUSI_USER', 'NABUSI_USER ROLE', NOW(), NOW(), 1);

-- Insert URL patterns
INSERT INTO url_pattern (method, url, action_name, description, created_date, last_updated_date, center_id)
VALUES
    ('GET', '/v1/admin/center/my-center', 'MY_CENTER_VIEW', '등록된 센터 조회', now(), now(), 1),
    ('POST', '/v1/admin/center/my-center', 'CREATE_CENTER', '센터 생성', now(), now(), 1),
    ('GET', '/v1/admin/invite/admin-member/to-me/*', 'INVITED_ADMIN_MEMBER_TO_ME_VIEW', '관리자 초대 조회', now(), now(), 1),
    ('PATCH', '/v1/admin/invite/admin-member/accept/*', 'INVITED_ADMIN_MEMBER_TO_ME_VIEW', '관리자 초대 수락', now(), now(), 1);


INSERT INTO role_url_pattern (role_id, url_pattern_id)
SELECT r.id AS role_id, u.id AS url_pattern_id
FROM role r
JOIN url_pattern u
    ON u.url IN ('/v1/admin/center/my-center', '/v1/admin/invite/admin-member/to-me/*', '/v1/admin/invite/admin-member/accept/*');