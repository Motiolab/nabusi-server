package com.motiolab.nabusi_server.memberPackage.member.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetHomeSummaryMobileResponse {
    private int attendanceCount;
    private int reservationCount;
    private int reviewCount;
}
