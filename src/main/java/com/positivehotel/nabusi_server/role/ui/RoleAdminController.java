package com.positivehotel.nabusi_server.role.ui;

import com.positivehotel.nabusi_server.role.application.RoleAdminService;
import com.positivehotel.nabusi_server.role.application.dto.RoleAdminDto;
import com.positivehotel.nabusi_server.role.application.dto.request.CreateRoleUrlPatternRequestV1;
import com.positivehotel.nabusi_server.role.application.dto.request.UpdateUrlPatternActionRequestV1;
import com.positivehotel.nabusi_server.role.application.dto.response.GetRoleAndUrlPatternAdminResponseV1;
import com.positivehotel.nabusi_server.role.application.dto.response.GetRoleInfoByCenterIdResponseV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RoleAdminController {
    private final RoleAdminService roleAdminService;

    @GetMapping("/v1/admin/role/info/{centerId}")
    public ResponseEntity<List<GetRoleInfoByCenterIdResponseV1>> getRoleInfoListByCenterId(@PathVariable Long centerId) {
        final List<RoleAdminDto> roleAdminDtoList = roleAdminService.getRoleListByCenterId(centerId);
        final List<GetRoleInfoByCenterIdResponseV1> roleInfoByCenterIdResponseV1List = roleAdminDtoList.stream()
                .map(roleAdminDto -> GetRoleInfoByCenterIdResponseV1.builder()
                        .id(roleAdminDto.getRoleDto().getId())
                        .name(roleAdminDto.getRoleDto().getName())
                        .build())
                .toList();

        return ResponseEntity.ok(roleInfoByCenterIdResponseV1List);
    }

    @GetMapping("/v1/admin/role/url-pattern/{centerId}")
    public ResponseEntity<List<GetRoleAndUrlPatternAdminResponseV1>> getRoleAndUrlPatternByCenterId(@PathVariable Long centerId) {
        final List<RoleAdminDto> roleAdminDtoList = roleAdminService.getRoleListByCenterId(centerId);
        final List<GetRoleAndUrlPatternAdminResponseV1> responseList = roleAdminDtoList.stream()
                .map(GetRoleAndUrlPatternAdminResponseV1::from)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    @PatchMapping("/v1/admin/role/url-pattern/action/{centerId}")
    public ResponseEntity<List<GetRoleAndUrlPatternAdminResponseV1>> updateRoleUrlPatternActionByCenterId(@PathVariable Long centerId, @RequestBody UpdateUrlPatternActionRequestV1 updateUrlPatternActionRequestV1) {
        roleAdminService.updateRoleUrlPatternActionByCenterId(centerId, updateUrlPatternActionRequestV1);
        return getRoleAndUrlPatternByCenterId(centerId);
    }

    @PatchMapping("/v1/admin/role/url-pattern/init-action/{centerId}")
    public ResponseEntity<List<GetRoleAndUrlPatternAdminResponseV1>> updateInitRoleUrlPatternActionByCenterId(@PathVariable Long centerId) {
        roleAdminService.updateInitRoleUrlPatternActionByCenterId(centerId);
        return getRoleAndUrlPatternByCenterId(centerId);
    }

    @PostMapping("/v1/admin/role/url-pattern/{cetnerId}")
    public ResponseEntity<List<GetRoleAndUrlPatternAdminResponseV1>> createRoleUrlPatternByCenterId(@PathVariable Long cetnerId, @RequestBody CreateRoleUrlPatternRequestV1 createRoleUrlPatternRequestV1) {
        roleAdminService.createRoleUrlPatternByCenterId(cetnerId, createRoleUrlPatternRequestV1.getRoleName());
        return getRoleAndUrlPatternByCenterId(cetnerId);
    }

    @DeleteMapping("/v1/admin/role/url-pattern/{centerId}/{roleId}")
    public ResponseEntity<List<GetRoleAndUrlPatternAdminResponseV1>> deleteRoleUrlPatternByCenterIdAndRoleId(@PathVariable Long centerId, @PathVariable Long roleId) {
        roleAdminService.deleteRoleUrlPatternByCenterIdAndRoleId(centerId, roleId);
        return getRoleAndUrlPatternByCenterId(centerId);
    }
}
