package com.positivehotel.nabusi_server.policy.wellnessClass.application;

import com.positivehotel.nabusi_server.policy.wellnessClass.application.dto.PolicyWellnessClassDto;
import com.positivehotel.nabusi_server.policy.wellnessClass.domain.PolicyWellnessClassEntity;
import com.positivehotel.nabusi_server.policy.wellnessClass.domain.PolicyWellnessClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyWellnessClassServiceImpl implements PolicyWellnessClassService {
    private final PolicyWellnessClassRepository policyWellnessClassRepository;

    @Override
    public void create(PolicyWellnessClassDto policyWellnessClassDto) {
        PolicyWellnessClassEntity newPolicyWellnessClass = PolicyWellnessClassEntity.create(
                policyWellnessClassDto.getCenterId(),
                policyWellnessClassDto.getReservationStart(),
                policyWellnessClassDto.getReservationEnd(),
                policyWellnessClassDto.getReservationCancelLimit(),
                policyWellnessClassDto.getAutoReserveBeforeClassTime(),
                policyWellnessClassDto.getAutoAbsentLimit(),
                policyWellnessClassDto.getIsActiveAutoReservation()
        );

        policyWellnessClassRepository.save(newPolicyWellnessClass);
    }

    @Override
    public PolicyWellnessClassDto getByCenterId(Long centerId) {
        return policyWellnessClassRepository.findByCenterId(centerId).map(PolicyWellnessClassDto::from).orElse(null);
    }

    @Override
    public void update(PolicyWellnessClassDto policyWellnessClassDto) {
         PolicyWellnessClassEntity policyWellnessClassEntity = policyWellnessClassRepository.findById(policyWellnessClassDto.getId()).orElseThrow(() -> new RuntimeException("Policy not found"));
         policyWellnessClassEntity.put(
                 policyWellnessClassDto.getCenterId(),
                 policyWellnessClassDto.getReservationStart(),
                 policyWellnessClassDto.getReservationEnd(),
                 policyWellnessClassDto.getReservationCancelLimit(),
                 policyWellnessClassDto.getAutoReserveBeforeClassTime(),
                 policyWellnessClassDto.getAutoAbsentLimit(),
                 policyWellnessClassDto.getIsActiveAutoReservation()
         );

        policyWellnessClassRepository.save(policyWellnessClassEntity);
    }
}
