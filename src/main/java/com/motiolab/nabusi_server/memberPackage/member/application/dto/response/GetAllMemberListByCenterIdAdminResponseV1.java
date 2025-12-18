package com.motiolab.nabusi_server.memberPackage.member.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
public class GetAllMemberListByCenterIdAdminResponseV1 {
    Long id;
    String name;
    String mobile;
    String roleName;
    String socialName;
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
        Integer unpaidValue;
        Long remainingDate;
        Integer totalUsableCnt;
        ZonedDateTime startDate;
    }

    @Builder
    @Getter
    public static class MemberMemo {
        Long id;
        String content;
    }
}
