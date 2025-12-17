package com.positivehotel.nabusi_server.memberPackage.member.application;

import com.positivehotel.nabusi_server.exception.customException.NotFoundException;
import com.positivehotel.nabusi_server.memberPackage.member.application.dto.MemberAdminDto;
import com.positivehotel.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.positivehotel.nabusi_server.memberPackage.memberMemo.application.MemberMemoService;
import com.positivehotel.nabusi_server.memberPackage.memberMemo.application.dto.MemberMemoDto;
import com.positivehotel.nabusi_server.role.application.RoleService;
import com.positivehotel.nabusi_server.role.application.dto.RoleDto;
import com.positivehotel.nabusi_server.security.DynamicRoleService;
import com.positivehotel.nabusi_server.socialUser.kakaoUser.application.KakaoUserService;
import com.positivehotel.nabusi_server.socialUser.kakaoUser.application.dto.KakaoUserDto;
import com.positivehotel.nabusi_server.socialUser.naverUser.application.NaverUserService;
import com.positivehotel.nabusi_server.socialUser.naverUser.application.dto.NaverUserDto;
import com.positivehotel.nabusi_server.teacher.application.TeacherService;
import com.positivehotel.nabusi_server.teacher.application.dto.TeacherDto;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.application.WellnessTicketIssuanceService;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.WellnessTicketIssuanceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberAdminServiceImpl implements MemberAdminService {
    private final MemberService memberService;
    private final RoleService roleService;
    private final DynamicRoleService dynamicRoleService;
    private final TeacherService teacherService;
    private final WellnessTicketIssuanceService wellnessTicketIssuanceService;
    private final MemberMemoService memberMemoService;
    private final KakaoUserService kakaoUserService;
    private final NaverUserService naverUserService;

    @Override
    public MemberAdminDto getMemberById(Long memberId) {
        final MemberDto memberDto = memberService.getById(memberId);
        if (memberDto == null) throw new NotFoundException(MemberDto.class, memberId);
        return MemberAdminDto.builder().memberDto(memberDto).build();
    }

    @Override
    public List<MemberAdminDto> getMemberListToAddTeacherByCenterId(Long centerId) {
        final List<MemberDto> memberDtoList = memberService.getAllByCenterIdIn(centerId);
        final List<TeacherDto> teacherDtoList = teacherService.getAllByCenterId(centerId);
        final List<Long> teacherMemberIdList = teacherDtoList.stream().map(TeacherDto::getMemberId).toList();

        return memberDtoList.stream()
                .filter(memberDto -> !teacherMemberIdList.contains(memberDto.getId()))
                .map(memberDto -> MemberAdminDto.builder().memberDto(memberDto).build())
                .toList();
    }

    @Override
    public List<MemberAdminDto> getAdminMemberListByCenterId(Long centerId) {
        return memberService.getAllByCenterIdIn(centerId).stream()
                .filter(memberDto -> memberDto.getRoleList().stream()
                        .anyMatch(roleDto -> roleDto.getCenterId().equals(centerId) && !roleDto.getName().equals("USER")))
                .map(memberDto -> MemberAdminDto.builder().memberDto(memberDto).build())
                .toList();
    }

    @Override
    public Boolean updateMemberRole(Long centerId, Long memberId, Long roleId) {
        final MemberDto memberDto = memberService.getById(memberId);
        final List<Long> memberRoleIdListByCenterId = memberDto.getRoleList().stream()
                .filter(roleDto -> roleDto.getCenterId().equals(centerId))
                .map(RoleDto::getId)
                .toList();

        memberRoleIdListByCenterId.forEach(memberRoleId -> memberService.deleteRole(memberId, memberRoleId));
        final MemberDto updatedMemberDto = memberService.addRole(memberId, roleId);
        dynamicRoleService.updateMember(updatedMemberDto);
        return updatedMemberDto != null;
    }

    @Override
    public Boolean deleteAdminRoleByMemberIdList(Long centerId, List<Long> memberIdList) {
        final List<MemberDto> memberDtoList = memberService.getAllByIdList(memberIdList);

        final RoleDto userRoleDto = roleService.getByNameAndCenterId("USER", centerId);
        if (userRoleDto == null) {
            throw new RuntimeException("USER Role not found.");
        }

        memberDtoList.forEach(memberDto -> {
            memberDto.getRoleList().stream()
                    .filter(role -> role.getCenterId().equals(centerId))
                    .map(RoleDto::getId)
                    .forEach(roleId -> memberService.deleteRole(memberDto.getId(), roleId));

            final MemberDto updatedMemberDto = memberService.addRole(memberDto.getId(), userRoleDto.getId());
            dynamicRoleService.updateMember(updatedMemberDto);
        });

        return true;
    }

    @Transactional
    @Override
    public Boolean passOwnerRole(Long memberId, Long centerId, Long targetMemberId) {
        final MemberDto ownerMemberDto = memberService.getById(memberId);
        final MemberDto targetMemberDto = memberService.getById(targetMemberId);

        if (ownerMemberDto == null || targetMemberDto == null) {
            throw new RuntimeException("MemberId not found.");
        }

        if (ownerMemberDto.getRoleList().stream()
                .noneMatch(roleDto -> roleDto.getCenterId().equals(centerId) && roleDto.getName().equals("OWNER"))) {
            throw new RuntimeException("Owner Role not found.");
        }

        if (targetMemberDto.getRoleList().stream()
                .noneMatch(roleDto -> roleDto.getCenterId().equals(centerId) && !roleDto.getName().equals("OWNER") && !roleDto.getName().equals("USER"))) {
            throw new RuntimeException("Admin Role not found.");
        }

        final RoleDto ownerRoleDto = roleService.getByNameAndCenterId("OWNER", centerId);
        if (ownerRoleDto == null) {
            throw new RuntimeException("OWNER Role not found.");
        }

        final RoleDto managerRoleDto = roleService.getByNameAndCenterId("MANAGER", centerId);
        if (managerRoleDto == null) {
            throw new RuntimeException("MANAGER Role not found.");
        }

        ownerMemberDto.getRoleList().stream()
                .filter(role -> role.getCenterId().equals(centerId))
                .map(RoleDto::getId)
                .forEach(roleId -> memberService.deleteRole(ownerMemberDto.getId(), roleId));
        MemberDto updatedOwnerMemberDto = memberService.addRole(ownerMemberDto.getId(), managerRoleDto.getId());

        targetMemberDto.getRoleList().stream()
                .filter(role -> role.getCenterId().equals(centerId))
                .map(RoleDto::getId)
                .forEach(roleId -> memberService.deleteRole(targetMemberDto.getId(), roleId));
        MemberDto updatedTargetMemberDto = memberService.addRole(targetMemberId, ownerRoleDto.getId());

        dynamicRoleService.updateMember(updatedOwnerMemberDto);
        dynamicRoleService.updateMember(updatedTargetMemberDto);
        return true;
    }

    @Override
    public List<MemberAdminDto> getAllMemberByCenterId(Long centerId) {
        return memberService.getAllByCenterIdIn(centerId)
                .stream()
                .map(memberDto -> MemberAdminDto.builder().memberDto(memberDto).build())
                .toList();
    }

    @Override
    public List<MemberAdminDto> getAllMemberListByCenterId(Long centerId) {
        final List<MemberDto> memberDtoList = memberService.getAllByCenterIdIn(centerId);
        final List<Long> memberIdList = memberDtoList.stream().map(MemberDto::getId).toList();

        final List<WellnessTicketIssuanceDto> wellnessTicketIssuanceDtoList = wellnessTicketIssuanceService.getAllByMemberIdListAndStartDateBeforeAndExpireDateAfterAndIsDeleteFalse(memberIdList);
        final List<MemberMemoDto> memberMemoDtoList = memberMemoService.getAllByMemberIdList(memberIdList);

        return memberDtoList.stream().map(memberDto -> {
            final List<WellnessTicketIssuanceDto> wellnessTicketIssuanceDtoListByMemberId = wellnessTicketIssuanceDtoList.stream()
                    .filter(wellnessTicketIssuanceDto -> wellnessTicketIssuanceDto.getMemberId().equals(memberDto.getId()))
                    .toList();

            final List<MemberAdminDto.MemberMemoExtension> memberMemoDtoListByMemberId = memberMemoDtoList.stream()
                    .filter(memberMemoDto -> memberMemoDto.getMemberId().equals(memberDto.getId()))
                    .map(memberMemoDto -> MemberAdminDto.MemberMemoExtension.builder()
                            .memberMemoDto(memberMemoDto)
                            .build())
                    .toList();

            return MemberAdminDto.builder()
                    .memberDto(memberDto)
                    .wellnessTicketIssuanceDtoList(wellnessTicketIssuanceDtoListByMemberId)
                    .memberMemoExtensionList(memberMemoDtoListByMemberId)
                    .build();
        }).toList();
    }

    @Override
    public MemberAdminDto getMemberDetailById(Long id) {
        final MemberDto memberDto = memberService.getById(id);
        final List<WellnessTicketIssuanceDto> wellnessTicketIssuanceDtoList = wellnessTicketIssuanceService.getAllByMemberId(memberDto.getId());
        final List<MemberMemoDto> memberMemoDtoList = memberMemoService.getAllByMemberId(memberDto.getId());
        final List<Long> registerMemberIdList = memberMemoDtoList.stream().map(MemberMemoDto::getMemberId).distinct().toList();
        final List<MemberDto> registerMemberDtoList = memberService.getAllByIdList(registerMemberIdList);

        final List<MemberAdminDto.MemberMemoExtension> memberMemoExtensionList = memberMemoDtoList
                .stream()
                .map(memberMemoDto -> MemberAdminDto.MemberMemoExtension.builder()
                        .memberMemoDto(memberMemoDto)
                        .registerMemberDto(registerMemberDtoList.stream()
                                .filter(memberDto1 -> memberDto1.getId().equals(memberMemoDto.getMemberId()))
                                .findFirst()
                                .orElse(null))
                        .build())
                .toList();

        final MemberAdminDto memberAdminDto =  MemberAdminDto.builder()
                .memberDto(memberDto)
                .wellnessTicketIssuanceDtoList(wellnessTicketIssuanceDtoList)
                .memberMemoExtensionList(memberMemoExtensionList)
                .build();

        if(memberDto.getSocialName().equals("kakao")) {
            final KakaoUserDto kakaoUserDto = kakaoUserService.getByMemberId(memberDto.getId());
            if(kakaoUserDto == null) throw new NotFoundException(KakaoUserDto.class, memberDto.getId());
            memberAdminDto.setKakaoUserDto(kakaoUserDto);
        }else if(memberDto.getSocialName().equals("naver")) {
            final NaverUserDto naverUserDto = naverUserService.getByMemberId(memberDto.getId());
            if(naverUserDto == null) throw new NotFoundException(NaverUserDto.class, memberDto.getId());
            memberAdminDto.setNaverUserDto(naverUserDto);
        }

        return memberAdminDto;
    }

}
