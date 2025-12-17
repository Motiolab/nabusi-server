package com.positivehotel.nabusi_server.memberPackage.member.application.dto;

import com.positivehotel.nabusi_server.memberPackage.memberMemo.application.dto.MemberMemoDto;
import com.positivehotel.nabusi_server.socialUser.kakaoUser.application.dto.KakaoUserDto;
import com.positivehotel.nabusi_server.socialUser.naverUser.application.dto.NaverUserDto;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.WellnessTicketIssuanceDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class MemberAdminDto {
    private MemberDto memberDto;
    private List<WellnessTicketIssuanceDto> wellnessTicketIssuanceDtoList;
    private KakaoUserDto kakaoUserDto;
    private NaverUserDto naverUserDto;
    private List<MemberMemoExtension> memberMemoExtensionList;


    @Builder
    @Getter
    public static class MemberMemoExtension {
        private MemberMemoDto memberMemoDto;
        private MemberDto registerMemberDto;
    }
}
