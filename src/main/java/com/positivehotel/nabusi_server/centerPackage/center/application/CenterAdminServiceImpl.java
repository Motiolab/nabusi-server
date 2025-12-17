package com.positivehotel.nabusi_server.centerPackage.center.application;

import com.positivehotel.nabusi_server.centerPackage.center.application.dto.*;
import com.positivehotel.nabusi_server.centerPackage.center.application.dto.request.CreateCenterRequestV1;
import com.positivehotel.nabusi_server.exception.customException.NotFoundException;
import com.positivehotel.nabusi_server.memberPackage.member.application.MemberService;
import com.positivehotel.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.positivehotel.nabusi_server.policy.wellnessClass.application.PolicyWellnessClassService;
import com.positivehotel.nabusi_server.policy.wellnessClass.application.dto.PolicyWellnessClassDto;
import com.positivehotel.nabusi_server.role.application.RoleService;
import com.positivehotel.nabusi_server.role.application.dto.RoleDto;
import com.positivehotel.nabusi_server.security.DynamicRoleService;
import com.positivehotel.nabusi_server.urlPattern.application.UrlPatternService;
import com.positivehotel.nabusi_server.urlPattern.application.dto.UrlPatternDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CenterAdminServiceImpl implements CenterAdminService{
    private final CenterService centerService;
    private final RoleService roleService;
    private final MemberService memberService;
    private final DynamicRoleService dynamicRoleService;
    private final CenterRoomService centerRoomService;
    private final CenterContactNumberService centerContactNumberService;
    private final CenterOpenInfoService centerOpenInfoService;
    private final UrlPatternService urlPatternService;
    private final PolicyWellnessClassService policyWellnessClassService;

    @Override
    public List<CenterAdminDto> getMyCenterList(Long memberId) {
        final List<CenterDto> centerDtoList = centerService.getAllByMemberId(memberId);
        final MemberDto memberDto = memberService.getById(memberId);
        if(memberDto == null) throw new NotFoundException(MemberDto.class, memberId);
        final List<RoleDto> roleDtoList = memberDto.getRoleList();

        return centerDtoList.stream()
                .map(centerDto -> {
                    final RoleDto myRoleDto = Optional.ofNullable(roleDtoList)
                            .flatMap(roles -> roles.stream()
                                    .filter(roleDto -> roleDto.getCenterId().equals(centerDto.getId()))
                                    .findFirst())
                            .orElse(null);

                    return CenterAdminDto.builder()
                            .centerDto(centerDto)
                            .myRoleDto(myRoleDto)
                            .build();
                }).toList();
    }

    @Transactional
    @Override
    public Boolean createCenter(Long memberId, CreateCenterRequestV1 createCenterRequestV1) {
        final CenterDto centerDto = CenterDto.builder()
                .name(createCenterRequestV1.getName())
                .address(createCenterRequestV1.getAddress())
                .code(generateRandomCode())
                .detailAddress(createCenterRequestV1.getDetailAddress())
                .roadName(createCenterRequestV1.getRoadName())
                .memberIdList(List.of(memberId))
                .build();
        final CenterDto newCenterDto = centerService.postCenter(centerDto);

        final List<String> roleNames = List.of("OWNER", "MANAGER", "TEACHER", "USER");
        final List<UrlPatternDto> urlPatternDtoList = UrlPatternManager.UrlPatternManager(newCenterDto.getId());
        urlPatternDtoList.forEach(urlPatternDto -> urlPatternDto.setUrl(urlPatternDto.getUrl().replaceAll("\\{centerId}", newCenterDto.getId().toString())));
        final List<UrlPatternDto> storedUrlPatternDtoList = urlPatternService.postAll(urlPatternDtoList);

        final List<RoleDto> roleDtoList = roleNames.stream()
                .map(roleName -> {
                    final List<String> actionNameList = UrlPatternManager.getActionNameListByRoleName(roleName);
                    final List<UrlPatternDto> urlPatternDtoListByActionList = storedUrlPatternDtoList.stream().filter(urlPatternDto -> actionNameList.contains(urlPatternDto.getActionName())).toList();

                    return RoleDto.builder()
                            .name(roleName)
                            .description(newCenterDto.getName() + " " + roleName + " ROLE")
                            .urlPatternDtoList(urlPatternDtoListByActionList)
                            .centerId(newCenterDto.getId())
                            .build();
                })
                .toList();

        final List<RoleDto> storedRoleDtoList = roleService.createRoleList(roleDtoList);
        final RoleDto adminRoleDto = storedRoleDtoList.stream()
                .filter(roleDto -> roleDto.getName().equals("OWNER") && roleDto.getCenterId().equals(newCenterDto.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("OWNER ROLE NOT FOUND"));
        memberService.addRole(memberId, adminRoleDto.getId());
        final MemberDto savedCenterInMemberDto = memberService.addCenterId(memberId, newCenterDto.getId());
        dynamicRoleService.updateMember(savedCenterInMemberDto);

        final PolicyWellnessClassDto newPolicyWellnessClassDto = PolicyWellnessClassDto.builder()
                .centerId(newCenterDto.getId())
                .reservationStart(10)
                .reservationEnd(10)
                .reservationCancelLimit(10)
                .autoReserveBeforeClassTime(10)
                .autoAbsentLimit(10)
                .isActiveAutoReservation(false)
                .build();
        policyWellnessClassService.create(newPolicyWellnessClassDto);
        return true;
    }

    @Override
    public CenterAdminDto getCenterInfo(Long centerId) {
        final CenterDto centerDto = centerService.getById(centerId);
        if(centerDto == null) {
            throw new RuntimeException("CENTER NOT FOUND");
        }

        final List<CenterRoomDto> centerRoomDtoList = centerRoomService.getAllByCenterId(centerId);
        final List<CenterOpenInfoDto> centerOpenInfoDtoList = centerOpenInfoService.getAllByCenterId(centerId);
        final List<CenterContactNumberDto> centerContactNumberDtoList = centerContactNumberService.getAllByCenterId(centerId);

        return CenterAdminDto.builder()
                .centerDto(centerDto)
                .centerRoomDtoList(centerRoomDtoList)
                .centerOpenInfoDtoList(centerOpenInfoDtoList)
                .centerContactNumberDtoList(centerContactNumberDtoList)
                .build();
    }

    @Transactional
    @Override
    public CenterAdminDto updateCenterInfo(CenterAdminDto centerAdminDto) {
        CenterDto updatedCenterDto = centerService.put(centerAdminDto.getCenterDto());
        centerRoomService.deleteByCenterId(updatedCenterDto.getId());
        centerRoomService.saveAll(centerAdminDto.getCenterRoomDtoList());
        centerContactNumberService.deleteByCenterId(updatedCenterDto.getId());
        centerContactNumberService.saveAll(centerAdminDto.getCenterContactNumberDtoList());
        centerOpenInfoService.deleteByCenterId(updatedCenterDto.getId());
        centerOpenInfoService.saveAll(centerAdminDto.getCenterOpenInfoDtoList());

        return getCenterInfo(centerAdminDto.getCenterDto().getId());
    }

    private String generateRandomCode() {
        Random random = new Random();
        return String.valueOf(random.nextInt(10)) +
                (char) (random.nextInt(26) + 'A') +
                random.nextInt(10) +
                (char) (random.nextInt(26) + 'A');
    }
}
