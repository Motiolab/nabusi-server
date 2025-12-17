package com.positivehotel.nabusi_server.socialUser.naverUser.application;


import com.positivehotel.nabusi_server.socialUser.naverUser.application.dto.NaverUserDto;
import com.positivehotel.nabusi_server.socialUser.naverUser.domain.NaverUserEntity;
import com.positivehotel.nabusi_server.socialUser.naverUser.domain.NaverUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NaverUserServiceImpl implements NaverUserService {
    private final NaverUserRepository naverUserRepository;

    @Override
    public Boolean post(final @NonNull NaverUserDto naverUserDto) {
        final NaverUserEntity naverUserEntity = NaverUserEntity.create(
                naverUserDto.getGender(),
                naverUserDto.getEmail(),
                naverUserDto.getMobile(),
                naverUserDto.getMobileE164(),
                naverUserDto.getName(),
                naverUserDto.getBirthday(),
                naverUserDto.getBirthyear(),
                naverUserDto.getMemberId());

        naverUserRepository.save(naverUserEntity);
        return true;
    }

    @Override
    public Boolean patch(final @NonNull NaverUserDto naverUserDto) {
        return naverUserRepository.findByMobileE164(naverUserDto.getMobileE164())
                .map(naverUserEntity -> naverUserEntity.patch(
                        naverUserDto.getGender(),
                        naverUserDto.getEmail(),
                        naverUserDto.getMobile(),
                        naverUserDto.getMobileE164(),
                        naverUserDto.getName(),
                        naverUserDto.getBirthday(),
                        naverUserDto.getBirthyear(),
                        naverUserDto.getMemberId()))
                .map(naverUserRepository::save)
                .isPresent();
    }

    @Override
    public Boolean patchById(final @NonNull Long id, final @NonNull NaverUserDto naverUserDto) {
        return naverUserRepository.findById(id)
                .map(naverUserEntity -> naverUserEntity.patch(
                        naverUserDto.getGender(),
                        naverUserDto.getEmail(),
                        naverUserDto.getMobile(),
                        naverUserDto.getMobileE164(),
                        naverUserDto.getName(),
                        naverUserDto.getBirthday(),
                        naverUserDto.getBirthyear(),
                        naverUserDto.getMemberId()))
                .map(naverUserRepository::save)
                .isPresent();
    }

    @Override
    public Optional<NaverUserDto> getByMobileE164(final @NonNull String mobileE164) {
        return naverUserRepository.findByMobileE164(mobileE164).map(NaverUserDto::from);
    }

    @Override
    public NaverUserDto getByMemberId(Long memberId) {
        return naverUserRepository.findByMemberId(memberId)
                .map(NaverUserDto::from)
                .orElse(null);
    }
}
