package com.positivehotel.nabusi_server.policy.wellnessClass.application;

import com.positivehotel.nabusi_server.policy.wellnessClass.application.dto.PolicyWellnessClassDto;

public interface PolicyWellnessClassService {
    void create(PolicyWellnessClassDto policyWellnessClassDto);
    PolicyWellnessClassDto getByCenterId(Long centerId);
    void update(PolicyWellnessClassDto policyWellnessClassDto);
}
