package com.motiolab.nabusi_server.centerPackage.center.application.dto;

import com.motiolab.nabusi_server.urlPattern.application.dto.UrlPatternDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UrlPatternManager {
    public static List<UrlPatternDto> UrlPatternManagerWhiteList() {
        List<UrlPatternDto> urlPatternDtoList = new ArrayList<>();
        urlPatternDtoList.add(UrlPatternDto.create("INVITED_ADMIN_MEMBER_TO_ME_VIEW", "/v1/admin/invite/admin-member/to-me/*", "GET", "관리자 초대 조회", null));
        urlPatternDtoList.add(UrlPatternDto.create("TEST_CHECK_CENTER_ROLE", "/v1/admin/member/*", "GET", "테스트 센터 역할 확인", null));

        return urlPatternDtoList;
    }

    public static List<UrlPatternDto> UrlPatternManager(Long centerId) {
        List<UrlPatternDto> urlPatternDtoList = new ArrayList<>();
        urlPatternDtoList.add(UrlPatternDto.create("HOME", "/v1/admin/schedule/{centerId}", "GET", "스케줄 조회", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("DASHBOARD", "/v1/admin/reservation/{centerId}/rate", "GET", "예약률 조회", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("DASHBOARD", "/v1/admin/checkIn/{centerId}/rate", "GET", "체크인률 조회", centerId));

        urlPatternDtoList.add(UrlPatternDto.create("MEMBER_LIST_VIEW", "/v1/admin/member/all/{centerId}", "GET", "멤버 조회", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("MEMBER_LIST_VIEW", "/v1/admin/member/{centerId}/detail/*", "GET", "멤버 상세 조회", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("MEMBER_MANAGE", "/v1/admin/member/{centerId}/add", "POST", "멤버 추가", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("MEMBER_MANAGE", "/v1/admin/member/memo/{centerId}", "POST", "멤버 메모 작성", centerId));

        urlPatternDtoList.add(UrlPatternDto.create("TEACHER_VIEW", "/v1/admin/teacher/list/{centerId}", "GET", "강사 리스트 조회", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("TEACHER_VIEW", "/v1/admin/teacher/detail/{centerId}", "GET", "강사 상세 조회", centerId));

        urlPatternDtoList.add(UrlPatternDto.create("TEACHER_MANAGE", "/v1/admin/teacher/add/{centerId}", "POST", "코치 등록", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("TEACHER_MANAGE", "/v1/admin/teacher/update/image/{centerId}", "PATCH", "강사 이미지 수정", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("TEACHER_MANAGE", "/v1/admin/teacher/update/introduce/{centerId}", "PATCH", "강사 자기소개, 닉네임 수정", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("TEACHER_MANAGE", "/v1/admin/teacher/update/career/{centerId}", "PATCH", "강사 이력 수정", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("TEACHER_MANAGE", "/v1/admin/teacher/delete/{centerId}", "DELETE", "강사 삭제", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("TEACHER_MANAGE", "/v1/admin/teacher/restore/{centerId}", "PATCH", "강사 복구", centerId));

        urlPatternDtoList.add(UrlPatternDto.create("ALL_CLASS_LIST_VIEW", "/v1/admin/wellness-lecture/list/{centerId}", "GET", "수업 리스트 조회", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("ALL_CLASS_LIST_VIEW", "/v1/admin/wellness-lecture/detail/{centerId}", "GET", "수업 상세 조회", centerId));

        urlPatternDtoList.add(UrlPatternDto.create("ALL_CLASS_MANAGE", "/v1/admin/wellness-class/name/{centerId}", "GET", "수업 생성 - 그룹 수업 조회", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("ALL_CLASS_MANAGE", "/v1/admin/wellness-class/{centerId}", "POST", "그룹 수업 생성", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("ALL_CLASS_MANAGE", "/v1/admin/wellness-class/detail/{centerId}", "GET", "그룹 수업 상세 조회", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("ALL_CLASS_MANAGE", "/v1/admin/wellness-class/detail/with-lecture/{centerId}", "GET", "그룹 수업 상세 & 수업 리스트 조회", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("ALL_CLASS_MANAGE", "/v1/admin/wellness-class/all/{centerId}", "GET", "그룹 수업 리스트 조회", centerId));

        urlPatternDtoList.add(UrlPatternDto.create("ALL_CLASS_MANAGE", "/v1/admin/wellness-lecture-type/name/{centerId}", "GET", "수업 종류 리스트 조회", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("ALL_CLASS_MANAGE", "/v1/admin/wellness-lecture-type/{centerId}", "POST", "수업 종류 리스트 생성", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("ALL_CLASS_MANAGE", "/v1/admin/member/list/add/teacher/{centerId}", "GET", "코치 등록을 위한 멤버 리스트 조회", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("ALL_CLASS_MANAGE", "/v1/admin/teacher/name/list/{centerId}", "GET", "수업 생성 - 코치 리스트 조회", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("ALL_CLASS_MANAGE", "/v1/admin/center/room/list/{centerId}", "GET", "수업 생성 - 수업 장소 조회", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("ALL_CLASS_MANAGE", "/v1/admin/wellness-lecture/list/{centerId}", "POST", "수업 리스트 생성", centerId));

        urlPatternDtoList.add(UrlPatternDto.create("ALL_CLASS_MANAGE", "/v1/admin/wellness-lecture/{centerId}", "DELETE", "수업 폐강", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("ALL_CLASS_MANAGE", "/v1/admin/wellness-lecture/{centerId}", "PUT", "수업 수정", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("ALL_CLASS_MANAGE", "/v1/admin/wellness-lecture/restore/{centerId}", "PATCH", "수업 복구", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("ALL_CLASS_MANAGE", "/v1/admin/wellness-ticket-management/name/{centerId}", "GET", "수업 생성 - 정기권 조회", centerId));

        urlPatternDtoList.add(UrlPatternDto.create("TICKET_VIEW", "/v1/admin/wellness-ticket/{centerId}", "GET", "수강권 목록 조회", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("TICKET_VIEW", "/v1/admin/wellness-ticket/detail/{centerId}", "GET", "수강권 상세 조회", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("TICKET_VIEW", "/v1/admin/wellness-ticket-issuance/list/wellness-ticket-id/{centerId}", "GET", "수강권 상세 조회 - 발급 리스트 조회", centerId));

        urlPatternDtoList.add(UrlPatternDto.create("TICKET_MANAGE", "/v1/admin/wellness-ticket/{centerId}", "DELETE", "수강권 판매 중지", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("TICKET_MANAGE", "/v1/admin/wellness-ticket/{centerId}", "POST", "수강권 생성", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("TICKET_MANAGE", "/v1/admin/wellness-ticket/restore/{centerId}", "PATCH", "수강권 복구", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("TICKET_MANAGE", "/v1/admin/wellness-ticket/{centerId}", "PUT", "수강권 수정", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("TICKET_MANAGE", "/v1/admin/wellness-ticket-issuance/{centerId}", "POST", "수강권 발급", centerId));

        urlPatternDtoList.add(UrlPatternDto.create("ISSUED_TICKET_MANAGE", "/v1/admin/wellness-ticket-issuance/update/detail/{centerId}", "GET", "발급 수강권 수정 - 조회", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("ISSUED_TICKET_MANAGE", "/v1/admin/wellness-ticket-issuance/update/{centerId}", "PUT", "발급 수강권 수정", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("ISSUED_TICKET_MANAGE", "/v1/admin/wellness-ticket-payment/unpaid/{centerId}", "POST", "발급 수강권 미수금 결제", centerId));

        urlPatternDtoList.add(UrlPatternDto.create("ISSUED_TICKET_EXTENSION", "/v1/admin/wellness-ticket-extension/{centerId}", "POST", "수강권 일괄 연장", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("ISSUED_TICKET_EXTENSION", "/v1/admin/wellness-ticket-extension/{centerId}", "GET", "수강권 일괄 연장 이력 조회", centerId));

        urlPatternDtoList.add(UrlPatternDto.create("RESERVATION_MANAGE", "/v1/admin/reservation/create/{centerId}", "POST", "수업 예약 생성", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("RESERVATION_MANAGE", "/v1/admin/reservation/list/{centerId}", "GET", "수업 예약 리스트 조회", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("RESERVATION_MANAGE", "/v1/admin/reservation/cancel/{centerId}", "PUT", "수업 예약 취소", centerId));

        urlPatternDtoList.add(UrlPatternDto.create("CENTER_INFO_VIEW", "/v1/admin/center/info/{centerId}", "GET", "센터 정보 조회", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("CENTER_INFO_MANAGE", "/v1/admin/center/info/{centerId}", "PUT", "센터 정보 수정", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("CENTER_INFO_MANAGE", "/v1/admin/center/room/{centerId}", "POST", "센터 장소 추가", centerId));

        urlPatternDtoList.add(UrlPatternDto.create("ROLE_MANAGE", "/v1/admin/role/url-pattern/{centerId}", "GET", "역할 상세 조회", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("ROLE_MANAGE", "/v1/admin/role/url-pattern/action/{centerId}", "PATCH", "역할의 권한 수정", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("ROLE_MANAGE", "/v1/admin/role/url-pattern/init-action/{centerId}", "PATCH", "역할의 권한 초기화", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("ROLE_MANAGE", "/v1/admin/role/url-pattern/{centerId}", "POST", "역할의 생성", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("ROLE_MANAGE", "/v1/admin/role/url-pattern/{centerId}/*", "DELETE", "역할의 삭제", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("CLASS_POLICY_MANAGE", "/v1/admin/policy/class/{centerId}", "GET", "수업 정책 조회", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("CLASS_POLICY_MANAGE", "/v1/admin/policy/class/{centerId}", "PUT", "수업 정책 수정", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("AUTH_PERMISSION_VIEW", "/v1/admin/member/admin/{centerId}", "GET", "관리자 멤버 조회", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("AUTH_PERMISSION_VIEW", "/v1/admin/role/info/{centerId}", "GET", "역할 리스트 조회", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("AUTH_PERMISSION_MANAGE", "/v1/admin/invite/admin-member/{centerId}", "POST", "관리자 초대장 전송", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("AUTH_PERMISSION_MANAGE", "/v1/admin/member/role/{centerId}", "PATCH", "관리자 역할 변경", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("AUTH_PERMISSION_MANAGE", "/v1/admin/member/role/{centerId}", "DELETE", "관리자 역할 삭제", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("AUTH_PERMISSION_MANAGE_OWNER", "/v1/admin/member/role/owner/pass/{centerId}", "PATCH", "OWNER 변경", centerId));
        urlPatternDtoList.add(UrlPatternDto.create("INVITED_ADMIN_MEMBER_TO_ME_VIEW", "/v1/admin/invite/admin-member/to-me/*", "GET", "관리자 초대 조회", 1L));

        urlPatternDtoList.add(UrlPatternDto.create("NOTICE_MANAGE", "/v1/admin/center/notice/{centerId}", "POST", "센터 공지사항 생성", 1L));
        urlPatternDtoList.add(UrlPatternDto.create("NOTICE_MANAGE", "/v1/admin/center/notice/list/{centerId}", "GET", "센터 공지사항 리스트 조회", 1L));
        urlPatternDtoList.add(UrlPatternDto.create("NOTICE_MANAGE", "/v1/admin/center/notice/detail/{centerId}", "GET", "센터 공지사항 상세 조회", 1L));
        urlPatternDtoList.add(UrlPatternDto.create("NOTICE_MANAGE", "/v1/admin/center/notice/update/{centerId}", "PUT", "센터 공지사항 수정", 1L));

        urlPatternDtoList.add(UrlPatternDto.create("IMAGE_UPLOAD", "/s3/upload", "POST", "이미지 업로드", 1L));

        urlPatternDtoList.add(UrlPatternDto.create("RESIGN_MEMBER", "/v1/mobile/member", "DELETE", "회원탈퇴", 1L));
        return urlPatternDtoList;
    }

    public static List<String> getActionNameListByRoleName(String roleName) {
        if (roleName.equals("NABUSI_OWNER")) {
            return Arrays.asList("MY_CENTER_VIEW", "CREATE_CENTER", "INVITED_ADMIN_MEMBER_TO_ME_VIEW", "IMAGE_UPLOAD");
        } else if (roleName.equals("NABUSI_USER")) {
            return Arrays.asList("MY_CENTER_VIEW", "CREATE_CENTER", "INVITED_ADMIN_MEMBER_TO_ME_VIEW", "IMAGE_UPLOAD", "RESIGN_MEMBER");
        } else if (roleName.equals("OWNER")) {
            return Arrays.asList("HOME", "DASHBOARD", "MEMBER_LIST_VIEW", "MEMBER_MANAGE", "CENTER_INFO_VIEW", "CENTER_INFO_MANAGE", "ROLE_MANAGE", "CLASS_POLICY_MANAGE", "AUTH_PERMISSION_VIEW", "AUTH_PERMISSION_MANAGE", "AUTH_PERMISSION_MANAGE_OWNER", "ALL_CLASS_MANAGE", "TEACHER_MANAGE", "TICKET_MANAGE", "TICKET_VIEW", "ALL_CLASS_LIST_VIEW", "ISSUED_TICKET_EXTENSION", "ISSUED_TICKET_MANAGE", "TEACHER_VIEW", "RESERVATION_MANAGE", "IMAGE_UPLOAD", "NOTICE_MANAGE");
        } else if (roleName.equals("MANAGER")) {
            return Arrays.asList("HOME", "DASHBOARD", "MEMBER_LIST_VIEW", "MEMBER_MANAGE", "CENTER_INFO_VIEW", "CENTER_INFO_MANAGE", "ROLE_MANAGE", "CLASS_POLICY_MANAGE", "AUTH_PERMISSION_VIEW", "AUTH_PERMISSION_MANAGE", "ALL_CLASS_MANAGE", "TEACHER_MANAGE", "TICKET_MANAGE", "TICKET_VIEW", "ALL_CLASS_LIST_VIEW", "ISSUED_TICKET_EXTENSION", "ISSUED_TICKET_MANAGE", "TEACHER_VIEW", "RESERVATION_MANAGE", "IMAGE_UPLOAD", "NOTICE_MANAGE");
        } else if (roleName.equals("TEACHER")) {
            return Arrays.asList("HOME", "DASHBOARD", "CENTER_INFO_MANAGE", "ROLE_MANAGE", "TICKET_VIEW", "ALL_CLASS_LIST_VIEW", "TEACHER_VIEW", "IMAGE_UPLOAD");
        } else if (roleName.equals("USER")) {
            return Arrays.asList("HOME", "DASHBOARD", "CENTER_INFO_MANAGE", "ROLE_MANAGE", "RESIGN_MEMBER");
        } else {
            return Arrays.asList("HOME", "DASHBOARD", "CENTER_INFO_MANAGE", "ROLE_MANAGE", "RESIGN_MEMBER");
        }
    }
}
