package com.motiolab.nabusi_server.invitationAdminMember.application;

import com.motiolab.nabusi_server.invitationAdminMember.application.dto.InvitationAdminMemberDto;
import com.motiolab.nabusi_server.invitationAdminMember.domain.InvitationAdminMemberEntity;
import com.motiolab.nabusi_server.invitationAdminMember.domain.InvitationAdminMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvitationAdminMemberServiceImpl implements InvitationAdminMemberService{
    private final InvitationAdminMemberRepository invitationAdminMemberRepository;
    @Override
    public void create(InvitationAdminMemberDto invitationAdminMemberDto) {
        InvitationAdminMemberEntity newInvitationAdminMemberEntity = InvitationAdminMemberEntity.create(
                invitationAdminMemberDto.getMobile(),
                invitationAdminMemberDto.getCenterId(),
                invitationAdminMemberDto.getIsAccept(),
                invitationAdminMemberDto.getSendAdminMemberId(),
                invitationAdminMemberDto.getRoleId(),
                invitationAdminMemberDto.getCode()
        );
        invitationAdminMemberRepository.save(newInvitationAdminMemberEntity);
    }

    @Override
    public InvitationAdminMemberDto getByCenterIdAndMobileAndIsAcceptFalse(Long centerId, String mobile) {
        return invitationAdminMemberRepository.findByCenterIdAndMobileAndIsAcceptFalse(centerId, mobile)
                .map(InvitationAdminMemberDto::from)
                .orElse(null);
    }

    @Override
    public void update(InvitationAdminMemberDto invitationAdminMemberDto) {
        invitationAdminMemberRepository.findById(invitationAdminMemberDto.getId())
                .map(invitationAdminMemberEntity -> {
                    invitationAdminMemberEntity.patch(
                            invitationAdminMemberDto.getMobile(),
                            invitationAdminMemberDto.getCenterId(),
                            invitationAdminMemberDto.getIsAccept(),
                            invitationAdminMemberDto.getSendAdminMemberId(),
                            invitationAdminMemberDto.getRoleId(),
                            invitationAdminMemberDto.getCode()
                    );
                    return invitationAdminMemberEntity;
                })
                .map(invitationAdminMemberRepository::save)
                .orElseThrow(() ->
                        new IllegalArgumentException("InvitationAdminMember not found with id: " + invitationAdminMemberDto.getId())
                );
    }

    @Override
    public InvitationAdminMemberDto getByMobileAndCode(String mobile, String code) {
        return invitationAdminMemberRepository.findByMobileAndCodeAndIsAcceptFalse(mobile, code)
                .map(InvitationAdminMemberDto::from)
                .orElse(null);
    }

    @Override
    public InvitationAdminMemberDto getById(Long id) {
        return invitationAdminMemberRepository.findById(id)
                .map(InvitationAdminMemberDto::from)
                .orElse(null);
    }
}
