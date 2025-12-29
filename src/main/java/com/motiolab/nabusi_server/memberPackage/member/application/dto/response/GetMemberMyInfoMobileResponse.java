package com.motiolab.nabusi_server.memberPackage.member.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMemberMyInfoMobileResponse {
    private String memberName;
    private String memberEmail;
    private String socialName;
    private Long totalPoints;
    private Boolean isExistClass;
}
