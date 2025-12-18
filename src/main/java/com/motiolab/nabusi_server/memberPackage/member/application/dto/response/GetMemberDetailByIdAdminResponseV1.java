package com.motiolab.nabusi_server.memberPackage.member.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class GetMemberDetailByIdAdminResponseV1 {
    Long id;
    String name;
    String mobile;
    String birthDay;
    String age;
    String gender;
    String email;
    String roleName;
    List<WellnessTicketIssuance> wellnessTicketIssuanceList;
    List<MemberMemo> memberMemoList;
    ZonedDateTime createdDate;

    @Builder
    @Getter
    public static class WellnessTicketIssuance {
        Long id;
        String name;
        String type;
        String backgroundColor;
        String textColor;
        String limitType;
        Integer limitCnt;
        Boolean isDelete;
        Integer remainingCnt;
        Long remainingDate;
        Integer totalUsableCnt;
        Integer unpaidValue;
        ZonedDateTime startDate;
        ZonedDateTime expireDate;
    }

    @Builder
    @Getter
    public static class MemberMemo {
        Long id;
        String content;
        String registerName;
        ZonedDateTime createdDate;
    }
}
