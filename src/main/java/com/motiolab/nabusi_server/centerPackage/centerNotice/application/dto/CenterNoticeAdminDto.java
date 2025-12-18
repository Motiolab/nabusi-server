package com.motiolab.nabusi_server.centerPackage.centerNotice.application.dto;

import com.motiolab.nabusi_server.memberPackage.member.application.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CenterNoticeAdminDto {
    private CenterNoticeDto centerNoticeDto;
    private MemberDto memberDto;
}
