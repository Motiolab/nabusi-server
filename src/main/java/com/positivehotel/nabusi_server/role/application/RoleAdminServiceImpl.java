package com.positivehotel.nabusi_server.role.application;

import com.positivehotel.nabusi_server.centerPackage.center.application.CenterService;
import com.positivehotel.nabusi_server.centerPackage.center.application.dto.CenterDto;
import com.positivehotel.nabusi_server.centerPackage.center.application.dto.UrlPatternManager;
import com.positivehotel.nabusi_server.memberPackage.member.application.MemberService;
import com.positivehotel.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.positivehotel.nabusi_server.role.application.dto.RoleAdminDto;
import com.positivehotel.nabusi_server.role.application.dto.RoleDto;
import com.positivehotel.nabusi_server.role.application.dto.request.UpdateUrlPatternActionRequestV1;
import com.positivehotel.nabusi_server.security.DynamicRoleService;
import com.positivehotel.nabusi_server.urlPattern.application.UrlPatternService;
import com.positivehotel.nabusi_server.urlPattern.application.dto.UrlPatternDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoleAdminServiceImpl implements RoleAdminService{
    private final RoleService roleService;
    private final UrlPatternService urlPatternService;
    private final CenterService centerService;
    private final DynamicRoleService dynamicRoleService;
    private final MemberService memberService;

    @Override
    public List<RoleAdminDto> getRoleListByCenterId(Long centerId) {
        List<RoleDto> roleDtoList = roleService.getAllByCenterId(centerId);

        return roleDtoList.stream()
                .filter(roleDto -> !roleDto.getName().equals("USER"))
                .map(roleDto -> RoleAdminDto.builder()
                        .roleDto(roleDto)
                        .build())
                .toList();
    }

    @Override
    public void updateRoleUrlPatternActionByCenterId(Long centerId, UpdateUrlPatternActionRequestV1 updateUrlPatternActionRequestV1) {
        final RoleDto roleDto = roleService.findAllByCenterIdAndName(centerId, updateUrlPatternActionRequestV1.getRoleName());
        if (roleDto == null) {
            throw new RuntimeException("Role not found");
        }

        Optional<UrlPatternDto> targetUrlPattern = roleDto.getUrlPatternDtoList().stream()
                .filter(urlPattern -> urlPattern.getActionName().equals(updateUrlPatternActionRequestV1.getActionName()))
                .findFirst();

        if (targetUrlPattern.isEmpty()) {
            List<UrlPatternDto> newUrlPatterns = urlPatternService.getAllByCenterIdAndActionName(centerId, updateUrlPatternActionRequestV1.getActionName());
            List<UrlPatternDto> updatedUrlPatterns = new ArrayList<>(roleDto.getUrlPatternDtoList());
            updatedUrlPatterns.addAll(newUrlPatterns);
            roleDto.setUrlPatternDtoList(updatedUrlPatterns);
        } else {
            List<UrlPatternDto> filteredUrlPatterns = roleDto.getUrlPatternDtoList().stream()
                    .filter(urlPattern -> !urlPattern.getActionName().equals(updateUrlPatternActionRequestV1.getActionName()))
                    .toList();
            roleDto.setUrlPatternDtoList(filteredUrlPatterns);
        }

        roleService.patch(roleDto);
        dynamicRoleService.updateMemberByCenterPermission(centerId);
    }

    @Override
    public void updateInitRoleUrlPatternActionByCenterId(Long centerId) {
        final CenterDto centerDto = centerService.getById(centerId);
        if (centerDto == null) {
            throw new RuntimeException("CENTER NOT FOUND");
        }

        final List<RoleDto> roleList = roleService.getAllByCenterId(centerId);
        final List<UrlPatternDto> existingUrlPatterns = new ArrayList<>(urlPatternService.getAllByCenterId(centerId));

        final List<UrlPatternDto> initialUrlPatterns = UrlPatternManager.UrlPatternManager(centerId);
        initialUrlPatterns.forEach(pattern ->
                pattern.setUrl(pattern.getUrl().replaceAll("\\{centerId}", centerId.toString()))
        );

        final List<UrlPatternDto> newUrlPatterns = initialUrlPatterns.stream()
                .filter(initPattern -> existingUrlPatterns.stream().noneMatch(existingPattern ->
                        existingPattern.getActionName().equals(initPattern.getActionName()) &&
                                existingPattern.getMethod().equals(initPattern.getMethod()) &&
                                existingPattern.getUrl().equals(initPattern.getUrl())
                ))
                .toList();

        if (!newUrlPatterns.isEmpty()) {
            final List<UrlPatternDto> persistedNewUrlPatterns = urlPatternService.postAll(newUrlPatterns);
            existingUrlPatterns.addAll(persistedNewUrlPatterns);
        }

        roleList.forEach(role -> {
            final List<String> actionNames = UrlPatternManager.getActionNameListByRoleName(role.getName());
            final List<UrlPatternDto> matchedPatterns = existingUrlPatterns.stream()
                    .filter(pattern -> actionNames.contains(pattern.getActionName()))
                    .toList();
            role.setUrlPatternDtoList(matchedPatterns);
        });

        roleService.updateAll(roleList);
        dynamicRoleService.updateMemberByCenterPermission(centerId);
    }

    @Override
    public void createRoleUrlPatternByCenterId(Long centerId, String roleName) {
        final CenterDto centerDto = centerService.getById(centerId);
        if(centerDto == null) {
            throw new RuntimeException("CENTER NOT FOUND");
        }
        final List<RoleDto> roleDtoList = roleService.getAllByCenterId(centerId);
        if(!roleDtoList.stream().filter(roleDto -> roleDto.getName().equals(roleName)).toList().isEmpty()) {
            throw new RuntimeException("ROLE ALREADY EXISTS");
        }

        final List<UrlPatternDto> savedUrlPatternDtoList = new ArrayList<>(roleDtoList.stream().flatMap(roleDto -> roleDto.getUrlPatternDtoList().stream()).toList());
        final List<String> actionNameList = UrlPatternManager.getActionNameListByRoleName(roleName);
        final List<UrlPatternDto> urlPatternDtoListByActionList = savedUrlPatternDtoList.stream().filter(urlPatternDto -> actionNameList.contains(urlPatternDto.getActionName())).toList();

        final RoleDto newRoleDto = RoleDto.builder()
                .name(roleName)
                .description(centerDto.getName() + " " + roleName + " ROLE")
                .centerId(centerId)
                .urlPatternDtoList(urlPatternDtoListByActionList)
                .build();

        roleService.create(newRoleDto);
        dynamicRoleService.updateMemberByCenterPermission(centerId);
    }

    @Override
    public void deleteRoleUrlPatternByCenterIdAndRoleId(Long centerId, Long roleId) {
        final RoleDto roleDto = roleService.getById(roleId);
        if(roleDto == null) {
            throw new RuntimeException("ROLE NOT FOUND");
        }
        if(roleDto.getName().equals("OWNER")) {
            throw new RuntimeException("OWNER ROLE CANNOT BE DELETED");
        }
        if(roleDto.getName().equals("MANAGE")) {
            throw new RuntimeException("MANAGE ROLE CANNOT BE DELETED");
        }
        final List<MemberDto> memberDtoList = memberService.getAllByRolesId(roleId);
        if(!memberDtoList.isEmpty()) {
            throw new RuntimeException("MEMBER HAS ROLE");
        }

        roleService.deleteById(roleId);
        dynamicRoleService.updateMemberByCenterPermission(centerId);
    }
}
