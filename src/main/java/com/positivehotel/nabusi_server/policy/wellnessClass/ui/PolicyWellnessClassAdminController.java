package com.positivehotel.nabusi_server.policy.wellnessClass.ui;

import com.positivehotel.nabusi_server.policy.wellnessClass.application.PolicyWellnessClassAdminServiceImpl;
import com.positivehotel.nabusi_server.policy.wellnessClass.application.dto.PolicyWellnessClassAdminDto;
import com.positivehotel.nabusi_server.policy.wellnessClass.application.dto.request.UpdatePolicyClassByCenterIdAdminRequestV1;
import com.positivehotel.nabusi_server.policy.wellnessClass.application.dto.response.GetPolicyClassByCenterIdAdminResponseV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PolicyWellnessClassAdminController {
    private final PolicyWellnessClassAdminServiceImpl policyWellnessClassAdminService;

    @GetMapping("/v1/admin/policy/class/{centerId}")
    public ResponseEntity<GetPolicyClassByCenterIdAdminResponseV1> getPolicyClassByCenterId(@PathVariable Long centerId) {
        final PolicyWellnessClassAdminDto policyWellnessClassAdminDto = policyWellnessClassAdminService.getByCenterId(centerId);

        final GetPolicyClassByCenterIdAdminResponseV1 getPolicyClassByCenterIdAdminResponseV1 = GetPolicyClassByCenterIdAdminResponseV1.builder()
                .id(policyWellnessClassAdminDto.getPolicyWellnessClassDto().getId())
                .centerId(policyWellnessClassAdminDto.getPolicyWellnessClassDto().getCenterId())
                .reservationStart(policyWellnessClassAdminDto.getPolicyWellnessClassDto().getReservationStart())
                .reservationEnd(policyWellnessClassAdminDto.getPolicyWellnessClassDto().getReservationEnd())
                .reservationCancelLimit(policyWellnessClassAdminDto.getPolicyWellnessClassDto().getReservationCancelLimit())
                .autoReserveBeforeClassTime(policyWellnessClassAdminDto.getPolicyWellnessClassDto().getAutoReserveBeforeClassTime())
                .autoAbsentLimit(policyWellnessClassAdminDto.getPolicyWellnessClassDto().getAutoAbsentLimit())
                .isActiveAutoReservation(policyWellnessClassAdminDto.getPolicyWellnessClassDto().getIsActiveAutoReservation())
                .build();

        return ResponseEntity.ok(getPolicyClassByCenterIdAdminResponseV1);
    }

    @PutMapping("/v1/admin/policy/class/{centerId}")
    public ResponseEntity<GetPolicyClassByCenterIdAdminResponseV1> updatePolicyClassByCenterId(@PathVariable Long centerId, @RequestBody UpdatePolicyClassByCenterIdAdminRequestV1 updatePolicyClassByCenterIdAdminRequestV1) {
        policyWellnessClassAdminService.updatePolicyClassByCenterId(centerId, updatePolicyClassByCenterIdAdminRequestV1);
        return getPolicyClassByCenterId(centerId);
    }
}
