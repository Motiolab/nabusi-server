package com.motiolab.nabusi_server.role.application;

import com.motiolab.nabusi_server.role.application.dto.RoleAdminDto;
import com.motiolab.nabusi_server.role.application.dto.request.UpdateUrlPatternActionRequestV1;

import java.util.List;

public interface RoleAdminService {
    List<RoleAdminDto> getRoleListByCenterId(Long centerId);
    void updateRoleUrlPatternActionByCenterId(Long centerId, UpdateUrlPatternActionRequestV1 updateUrlPatternActionRequestV1);
    void updateInitRoleUrlPatternActionByCenterId(Long centerId);
    void createRoleUrlPatternByCenterId(Long centerId, String roleName);
    void deleteRoleUrlPatternByCenterIdAndRoleId(Long centerId, Long roleId);
}
