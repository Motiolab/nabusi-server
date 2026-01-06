-- member 테이블에 is_delete 컬럼 추가
ALTER TABLE `member` ADD COLUMN `is_delete` TINYINT(1) NOT NULL DEFAULT 0;

-- 기존 탈퇴 회원(전화번호가 한글로 변환된 회원)들에 대해 is_delete를 1(true)로 수정
UPDATE `member` SET `is_delete` = 1 WHERE `mobile` REGEXP '[공일이삼사오육칠팔구]';
