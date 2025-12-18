package com.motiolab.nabusi_server.security;

import com.motiolab.nabusi_server.memberPackage.member.application.MemberService;
import com.motiolab.nabusi_server.memberPackage.member.application.dto.MemberDto;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
public class DynamicRoleService {
    private final MemberService memberService;
    private List<MemberDto> allMemberDtoList;

    @PostConstruct
    public void updateAllMemberEntityList() {
        allMemberDtoList = memberService.getAll();
    }

    public void updateMember(MemberDto updatedMemberDto) {
        final List<MemberDto> newMemberDtoList = new ArrayList<>(allMemberDtoList.stream()
                .filter(memberDto -> !memberDto.getId().equals(updatedMemberDto.getId()))
                .toList());

        newMemberDtoList.add(updatedMemberDto);
        allMemberDtoList = newMemberDtoList;
    }

    public void updateMemberByCenterPermission(Long centerId) {
        final List<MemberDto> newMemberDtoList = new ArrayList<>(allMemberDtoList.stream()
                .filter(memberDto -> !memberDto.getCenterIdList().contains(centerId))
                .toList());

        List<MemberDto> memberDtoList = memberService.getAllByCenterIdIn(centerId);
        newMemberDtoList.addAll(memberDtoList);
        allMemberDtoList = newMemberDtoList;
    }
}