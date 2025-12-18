package com.motiolab.nabusi_server.socialUser.kakaoUser.application;


import com.motiolab.nabusi_server.socialUser.kakaoUser.application.dto.KakaoUserDto;
import com.motiolab.nabusi_server.socialUser.kakaoUser.domain.KakaoUserEntity;
import com.motiolab.nabusi_server.socialUser.kakaoUser.domain.KakaoUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KakaoUserServiceImpl implements KakaoUserService {
    private final KakaoUserRepository kakaoUserRepository;

    @Override
    public Boolean post(final @NonNull KakaoUserDto kakaoUserDto) {
        final KakaoUserEntity kakaoUserEntity = KakaoUserEntity.create(
                kakaoUserDto.getUserId(),
                kakaoUserDto.getEmail(),
                kakaoUserDto.getBirthDay(),
                kakaoUserDto.getBirthYear(),
                kakaoUserDto.getGender(),
                kakaoUserDto.getPhoneNumber(),
                kakaoUserDto.getName(),
                kakaoUserDto.getNickName(),
                kakaoUserDto.getMemberId());

        kakaoUserRepository.save(kakaoUserEntity);
        return true;
    }

    @Override
    public Boolean patch(final @NonNull KakaoUserDto kakaoUserDto) {
        return kakaoUserRepository.findById(kakaoUserDto.getId())
                .map(kakaoUserEntity -> kakaoUserEntity.patch(kakaoUserDto.getUserId(), kakaoUserDto.getEmail(), kakaoUserDto.getBirthDay(), kakaoUserDto.getBirthYear(), kakaoUserDto.getGender(), kakaoUserDto.getPhoneNumber(), kakaoUserDto.getName(), kakaoUserDto.getNickName(), kakaoUserDto.getMemberId()))
                .map(kakaoUserRepository::save)
                .isPresent();
    }

    @Override
    public KakaoUserDto getByPhoneNumber(final @NonNull String phoneNumber) {
        return kakaoUserRepository.findByPhoneNumber(phoneNumber)
                .map(KakaoUserDto::from)
                .orElse(null);
    }

    @Override
    public KakaoUserDto getByMemberId(Long memberId) {
        return kakaoUserRepository.findByMemberId(memberId)
                .map(KakaoUserDto::from)
                .orElse(null);
    }
}
