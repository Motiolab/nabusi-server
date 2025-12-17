package com.positivehotel.nabusi_server.policy.wellnessClass.application;

import com.positivehotel.nabusi_server.policy.wellnessClass.application.dto.PolicyWellnessClassAdminDto;
import com.positivehotel.nabusi_server.policy.wellnessClass.application.dto.PolicyWellnessClassDto;
import com.positivehotel.nabusi_server.policy.wellnessClass.application.dto.request.UpdatePolicyClassByCenterIdAdminRequestV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyWellnessClassAdminServiceImpl implements PolicyWellnessClassAdminService {
    private final PolicyWellnessClassService policyWellnessClassService;


    @Override
    public PolicyWellnessClassAdminDto getByCenterId(Long centerId) {
        PolicyWellnessClassDto policyWellnessClassDto = policyWellnessClassService.getByCenterId(centerId);
        return PolicyWellnessClassAdminDto.builder().policyWellnessClassDto(policyWellnessClassDto).build();
    }

    @Override
    public void updatePolicyClassByCenterId(Long centerId, UpdatePolicyClassByCenterIdAdminRequestV1 updatePolicyClassByCenterIdAdminRequestV1) {
        PolicyWellnessClassDto policyWellnessClassDto = PolicyWellnessClassDto.builder()
                .id(updatePolicyClassByCenterIdAdminRequestV1.getId())
                .centerId(updatePolicyClassByCenterIdAdminRequestV1.getCenterId())
                .reservationStart(updatePolicyClassByCenterIdAdminRequestV1.getReservationStart())
                .reservationEnd(updatePolicyClassByCenterIdAdminRequestV1.getReservationEnd())
                .reservationCancelLimit(updatePolicyClassByCenterIdAdminRequestV1.getReservationCancelLimit())
                .autoReserveBeforeClassTime(updatePolicyClassByCenterIdAdminRequestV1.getAutoReserveBeforeClassTime())
                .autoAbsentLimit(updatePolicyClassByCenterIdAdminRequestV1.getAutoAbsentLimit())
                .isActiveAutoReservation(updatePolicyClassByCenterIdAdminRequestV1.getIsActiveAutoReservation())
                .build();

        policyWellnessClassService.update(policyWellnessClassDto);
    }
}
