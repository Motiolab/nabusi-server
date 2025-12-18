package com.motiolab.nabusi_server.reservation.application.dto;

import com.motiolab.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.motiolab.nabusi_server.memberPackage.memberMemo.application.dto.MemberMemoDto;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.WellnessTicketIssuanceDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReservationAdminDto {
    private ReservationDto reservationDto;
    private MemberExtension memberExtension;
    private WellnessTicketIssuanceDto wellnessTicketIssuanceDto;

    @Builder
    @Getter
    public static class MemberExtension {
        private MemberDto memberDto;
        private MemberMemoDto memberMemoDto;
    }
}
