package com.motiolab.nabusi_server.invitationAdminMember.ui;

import com.motiolab.nabusi_server.argumentResolver.MemberId;
import com.motiolab.nabusi_server.invitationAdminMember.application.InvitationAdminMemberAdminService;
import com.motiolab.nabusi_server.invitationAdminMember.application.dto.InvitationAdminMemberAdminDto;
import com.motiolab.nabusi_server.invitationAdminMember.application.dto.request.InviteAdminMemberRequestV1;
import com.motiolab.nabusi_server.invitationAdminMember.application.dto.response.GetInviteAdminMemberToMeResponseV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class InvitationAdminMemberAdminController {
    private final InvitationAdminMemberAdminService invitationAdminMemberAdminService;

    @PostMapping("/v1/admin/invite/admin-member/{centerId}")
    public ResponseEntity<Boolean> inviteAdminMemberByCenterId(@MemberId Long memberId, @PathVariable Long centerId, @RequestBody InviteAdminMemberRequestV1 inviteAdminMemberRequestV1) {
        invitationAdminMemberAdminService.inviteAdminMemberByCenterId(centerId, memberId, inviteAdminMemberRequestV1);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/v1/admin/invite/admin-member/to-me/{code}")
    public ResponseEntity<GetInviteAdminMemberToMeResponseV1> getInviteAdminMemberToMe(@MemberId Long memberId, @PathVariable String code) {
        final InvitationAdminMemberAdminDto invitationAdminMemberAdminDto = invitationAdminMemberAdminService.getInviteAdminMemberToMe(memberId, code);
        if(invitationAdminMemberAdminDto == null) {
            return ResponseEntity.ok(null);
        }

        final GetInviteAdminMemberToMeResponseV1 getInviteAdminMemberToMeResponseV1 = GetInviteAdminMemberToMeResponseV1.builder()
                .invitationAdminMemberId(invitationAdminMemberAdminDto.getInvitationAdminMemberDto().getId())
                .memberName(invitationAdminMemberAdminDto.getMyMemberDto().getName())
                .centerId(invitationAdminMemberAdminDto.getCenterDto().getId())
                .sendAdminMemberName(invitationAdminMemberAdminDto.getSendAdminMemberDto().getName())
                .roleId(invitationAdminMemberAdminDto.getInvitationAdminMemberDto().getRoleId())
                .centerName(invitationAdminMemberAdminDto.getCenterDto().getName())
                .centerAddress(invitationAdminMemberAdminDto.getCenterDto().getAddress())
                .centerDetailAddress(invitationAdminMemberAdminDto.getCenterDto().getDetailAddress())
                .build();
        return ResponseEntity.ok(getInviteAdminMemberToMeResponseV1);
    }

    @PatchMapping("/v1/admin/invite/admin-member/accept/{invitationAdminMemberId}")
    public ResponseEntity<Boolean> acceptInviteAdminMember(@MemberId Long memberId, @PathVariable Long invitationAdminMemberId) {
        invitationAdminMemberAdminService.acceptInviteAdminMember(memberId, invitationAdminMemberId);
        return ResponseEntity.ok(true);
    }
}
