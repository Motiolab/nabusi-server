package com.motiolab.nabusi_server.memberPackage.member.ui;

import com.motiolab.nabusi_server.argumentResolver.MemberId;
import com.motiolab.nabusi_server.memberPackage.member.application.MemberMobileService;
import com.motiolab.nabusi_server.memberPackage.member.application.dto.response.DeleteMemberResponse;
import com.motiolab.nabusi_server.memberPackage.member.application.dto.response.GetHomeSummaryMobileResponse;
import com.motiolab.nabusi_server.memberPackage.member.application.dto.response.GetMemberMyInfoMobileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/v1/mobile/member/home/summary")
    public ResponseEntity<GetHomeSummaryMobileResponse> getHomeSummary(@MemberId Long memberId) {
        return ResponseEntity.ok(memberMobileService.getHomeSummary(memberId));
    }

    @GetMapping("/v1/mobile/member/my-info")
    public ResponseEntity<GetMemberMyInfoMobileResponse> getMyInfo(@MemberId Long memberId) {
        return ResponseEntity.ok(memberMobileService.getMyInfo(memberId));
    }
}
