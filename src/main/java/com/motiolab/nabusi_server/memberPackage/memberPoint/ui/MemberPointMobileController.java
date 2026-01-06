package com.motiolab.nabusi_server.memberPackage.memberPoint.ui;

import com.motiolab.nabusi_server.argumentResolver.MemberId;
import com.motiolab.nabusi_server.memberPackage.memberPoint.application.MemberPointMobileService;
import com.motiolab.nabusi_server.memberPackage.memberPoint.application.dto.request.UpdateMemberPointMobileRequestV1;
import com.motiolab.nabusi_server.memberPackage.memberPoint.application.dto.response.GetMemberPointMobileResponseV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberPointMobileController {
    private final MemberPointMobileService memberPointMobileService;

    @PutMapping("/v1/admin/member/point")
    public ResponseEntity<Boolean> updateMemberPoint(@MemberId Long memberId, @RequestBody UpdateMemberPointMobileRequestV1 updateMemberPointMobileRequestV1) {
        updateMemberPointMobileRequestV1.setMemberId(memberId);
        memberPointMobileService.updateMemberPoint(updateMemberPointMobileRequestV1);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/v1/admin/member/point")
    public ResponseEntity<GetMemberPointMobileResponseV1> getMemberPointByMemberId(@MemberId Long memberId) {
        Long currentPoint = memberPointMobileService.getMemberPointByMemberId(memberId);
        return ResponseEntity.ok(GetMemberPointMobileResponseV1.builder().currentPoint(currentPoint).build());
    }
}
