package com.positivehotel.nabusi_server.memberPackage.member.ui;

import com.positivehotel.nabusi_server.argumentResolver.MemberId;
import com.positivehotel.nabusi_server.memberPackage.member.application.MemberMobileService;
import com.positivehotel.nabusi_server.memberPackage.member.application.dto.response.DeleteMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberMobileController {
    private final MemberMobileService memberMobileService;

    @DeleteMapping("/v1/mobile/member")
    public ResponseEntity<DeleteMemberResponse> deleteMember(@MemberId Long memberId) {
        final Boolean isSuccess = memberMobileService.delete(memberId);
        return ResponseEntity.ok(DeleteMemberResponse.builder().success(isSuccess).build());
    }
}
