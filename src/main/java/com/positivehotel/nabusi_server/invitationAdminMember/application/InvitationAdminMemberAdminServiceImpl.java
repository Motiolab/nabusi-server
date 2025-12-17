package com.positivehotel.nabusi_server.invitationAdminMember.application;

import com.positivehotel.nabusi_server.centerPackage.center.application.CenterService;
import com.positivehotel.nabusi_server.centerPackage.center.application.dto.CenterDto;
import com.positivehotel.nabusi_server.invitationAdminMember.application.dto.InvitationAdminMemberAdminDto;
import com.positivehotel.nabusi_server.invitationAdminMember.application.dto.InvitationAdminMemberDto;
import com.positivehotel.nabusi_server.invitationAdminMember.application.dto.request.InviteAdminMemberRequestV1;
import com.positivehotel.nabusi_server.memberPackage.member.application.MemberService;
import com.positivehotel.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.positivehotel.nabusi_server.notificationPackage.notificationSms.application.NotificationSmsService;
import com.positivehotel.nabusi_server.notificationPackage.notificationSms.application.dto.NotificationDto;
import com.positivehotel.nabusi_server.notificationPackage.notificationSms.application.dto.SendSmsResponse;
import com.positivehotel.nabusi_server.role.application.RoleService;
import com.positivehotel.nabusi_server.role.application.dto.RoleDto;
import com.positivehotel.nabusi_server.security.DynamicRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvitationAdminMemberAdminServiceImpl implements InvitationAdminMemberAdminService {
    private final InvitationAdminMemberService invitationAdminMemberService;
    private final CenterService centerService;
    private final NotificationSmsService notificationSmsService;
    private final MemberService memberService;
    private final DynamicRoleService dynamicRoleService;
    private final RoleService roleService;

    @Override
    public void inviteAdminMemberByCenterId(Long centerId, Long sendAdminMemberId, InviteAdminMemberRequestV1 inviteAdminMemberRequestV1) {
        final CenterDto centerDto = centerService.getById(centerId);
        if(centerDto == null) {
            throw new IllegalArgumentException("centerId is not found");
        }

        final RoleDto roleDto = roleService.getById(inviteAdminMemberRequestV1.getRoleId());
        if(roleDto == null) {
            throw new IllegalArgumentException("roleId is not found");
        }

        final NotificationDto notificationDto = NotificationDto.builder()
                        .centerName(centerDto.getName())
                        .mobile(inviteAdminMemberRequestV1.getMobile().split(" ")[1])
                        .roleName(roleDto.getName())
                        .code(centerDto.getCode())
                        .build();

        final SendSmsResponse sendSmsResponse = notificationSmsService.sendSms(notificationDto);
        if(!sendSmsResponse.getStatusCode().equals("202")) {
            throw new IllegalArgumentException("sms send fail");
        }

        final InvitationAdminMemberDto invitationAdminMemberDto = invitationAdminMemberService.getByCenterIdAndMobileAndIsAcceptFalse(centerId, inviteAdminMemberRequestV1.getMobile());
        if(invitationAdminMemberDto != null) {
            invitationAdminMemberDto.setSendAdminMemberId(sendAdminMemberId);
            invitationAdminMemberDto.setRoleId(roleDto.getId());
            invitationAdminMemberService.update(invitationAdminMemberDto);
        }else{
            InvitationAdminMemberDto newInvitationAdminMemberDto = InvitationAdminMemberDto.builder()
                    .mobile(inviteAdminMemberRequestV1.getMobile())
                    .centerId(centerId)
                    .isAccept(false)
                    .sendAdminMemberId(sendAdminMemberId)
                    .roleId(roleDto.getId())
                    .code(centerDto.getCode())
                    .build();
            invitationAdminMemberService.create(newInvitationAdminMemberDto);
        }
    }

    @Override
    public InvitationAdminMemberAdminDto getInviteAdminMemberToMe(Long memberId, String code) {
        final MemberDto memberDto = memberService.getById(memberId);
        if(memberDto == null) {
            throw new IllegalArgumentException("memberId is not found");
        }

        final String mobile = memberDto.getMobile();
        final InvitationAdminMemberDto invitationAdminMemberDto = invitationAdminMemberService.getByMobileAndCode(mobile, code);
        if(invitationAdminMemberDto == null) {
            return null;
        }

        final MemberDto sendMemberDto = memberService.getById(invitationAdminMemberDto.getSendAdminMemberId());
        if(sendMemberDto == null) {
            throw new IllegalArgumentException("sendAdminMemberId is not found");
        }

        final CenterDto centerDto = centerService.getById(invitationAdminMemberDto.getCenterId());
        if(centerDto == null) {
            throw new IllegalArgumentException("centerId is not found");
        }

        List<RoleDto> roleDtoListByCenterId = memberDto.getRoleList().stream().filter(roleDto -> roleDto.getCenterId().equals(centerDto.getId())).toList();
        List<RoleDto> roleDtoList = roleDtoListByCenterId.stream().filter(roleDto -> !roleDto.getName().equals("USER")).toList();
        if (!roleDtoList.isEmpty()) {
            return null;
        }

        return InvitationAdminMemberAdminDto.builder()
                .invitationAdminMemberDto(invitationAdminMemberDto)
                .myMemberDto(memberDto)
                .sendAdminMemberDto(sendMemberDto)
                .centerDto(centerDto)
                .build();
    }

    @Override
    public void acceptInviteAdminMember(Long memberId, Long invitationAdminMemberId) {
        final MemberDto memberDto = memberService.getById(memberId);
        if(memberDto == null) {
            throw new IllegalArgumentException("memberId is not found");
        }

        final InvitationAdminMemberDto invitationAdminMemberDto = invitationAdminMemberService.getById(invitationAdminMemberId);
        if(invitationAdminMemberDto == null) {
            throw new IllegalArgumentException("invitationAdminMemberId is not found");
        }

        if(!invitationAdminMemberDto.getMobile().equals(memberDto.getMobile())){
            throw new IllegalArgumentException("mobile is not match");
        }

        if(invitationAdminMemberDto.getIsAccept()){
            throw new IllegalArgumentException("invitationAdminMemberId is already accepted");
        }

        invitationAdminMemberDto.setIsAccept(true);
        invitationAdminMemberService.update(invitationAdminMemberDto);

        if (!memberDto.getCenterIdList().contains(invitationAdminMemberDto.getCenterId())) {
            memberService.addCenterId(memberDto.getId(), invitationAdminMemberDto.getCenterId());
        }
        memberService.addRoleListByNameAndCenterId(memberDto.getId(), invitationAdminMemberDto.getRoleId(), invitationAdminMemberDto.getCenterId());
        dynamicRoleService.updateMember(memberService.getById(memberId));
    }
}
