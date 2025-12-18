package com.motiolab.nabusi_server.memberPackage.memberMemo.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CreateMemberMemoAdminRequestV1 {
    private String content;
    private Long memberId;
    private Long registerId;
}
