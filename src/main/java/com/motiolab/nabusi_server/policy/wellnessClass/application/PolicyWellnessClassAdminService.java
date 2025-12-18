package com.motiolab.nabusi_server.policy.wellnessClass.application;

import com.motiolab.nabusi_server.policy.wellnessClass.application.dto.PolicyWellnessClassAdminDto;
import com.motiolab.nabusi_server.policy.wellnessClass.application.dto.request.UpdatePolicyClassByCenterIdAdminRequestV1;

public interface PolicyWellnessClassAdminService {
    PolicyWellnessClassAdminDto getByCenterId(Long centerId);
    void updatePolicyClassByCenterId(Long centerId, UpdatePolicyClassByCenterIdAdminRequestV1 updatePolicyClassByCenterIdAdminRequestV1);
}
