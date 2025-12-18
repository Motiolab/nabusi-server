package com.motiolab.nabusi_server.fcmTokenMobile.application;

import com.motiolab.nabusi_server.exception.customException.NotFoundException;
import com.motiolab.nabusi_server.fcmTokenMobile.domain.FcmTokenMobileEntity;
import com.motiolab.nabusi_server.fcmTokenMobile.domain.FcmTokenMobileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FcmTokenMobileServiceImpl implements FcmTokenMobileService{
    private final FcmTokenMobileRepository fcmTokenMobileRepository;

    @Override
    public void createOrUpdateFcmTokenMobile(String token, Long memberId) {
        final Optional<FcmTokenMobileEntity> optionalFcmTokenMobileEntity = fcmTokenMobileRepository.findByMemberId(memberId);
        if(optionalFcmTokenMobileEntity.isEmpty()) {
            final FcmTokenMobileEntity fcmTokenMobileEntity = FcmTokenMobileEntity.create(token, memberId, true);
            fcmTokenMobileRepository.save(fcmTokenMobileEntity);
        }else{
            final FcmTokenMobileEntity fcmTokenMobileEntity = optionalFcmTokenMobileEntity.get();
            fcmTokenMobileEntity.update(token, memberId);
            fcmTokenMobileEntity.updateIsLogin(true);
            fcmTokenMobileRepository.save(fcmTokenMobileEntity);
        }
    }

    @Override
    public void updateIsLogoutByMobile(Long memberId) {
        final FcmTokenMobileEntity fcmTokenMobileEntity = fcmTokenMobileRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException(FcmTokenMobileEntity.class, memberId));

        fcmTokenMobileEntity.updateIsLogin(false);
        fcmTokenMobileRepository.save(fcmTokenMobileEntity);
    }
}
