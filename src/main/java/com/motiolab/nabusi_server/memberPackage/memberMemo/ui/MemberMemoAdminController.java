package com.motiolab.nabusi_server.memberPackage.memberMemo.ui;

import com.motiolab.nabusi_server.argumentResolver.MemberId;
import com.motiolab.nabusi_server.memberPackage.memberMemo.application.MemberMemoAdminService;
import com.motiolab.nabusi_server.memberPackage.memberMemo.application.dto.request.CreateMemberMemoAdminRequestV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberMemoAdminController {
    private final MemberMemoAdminService memberMemoAdminService;

    @PostMapping("/v1/admin/member/memo/{centerId}")
    public ResponseEntity<Boolean> createMemberMemo(@MemberId Long memberId, @PathVariable Long centerId, @RequestBody CreateMemberMemoAdminRequestV1 createMemberMemoAdminRequestV1) {
        createMemberMemoAdminRequestV1.setRegisterId(memberId);
        memberMemoAdminService.createMemberMemo(createMemberMemoAdminRequestV1);
        return ResponseEntity.ok(true);
    }
}
