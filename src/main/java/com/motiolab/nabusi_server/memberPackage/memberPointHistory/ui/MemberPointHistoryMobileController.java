package com.motiolab.nabusi_server.memberPackage.memberPointHistory.ui;

import com.motiolab.nabusi_server.argumentResolver.MemberId;
import com.motiolab.nabusi_server.memberPackage.memberPointHistory.application.MemberPointHistoryMobileService;
import com.motiolab.nabusi_server.memberPackage.memberPointHistory.application.dto.response.GetMemberPointHistoryMobileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberPointHistoryMobileController {
    private final MemberPointHistoryMobileService memberPointHistoryMobileService;

    @GetMapping("/v1/mobile/member/point/history")
    public ResponseEntity<List<GetMemberPointHistoryMobileResponse>> getPointHistory(@MemberId Long memberId) {
        return ResponseEntity.ok(memberPointHistoryMobileService.getPointHistory(memberId));
    }
}
