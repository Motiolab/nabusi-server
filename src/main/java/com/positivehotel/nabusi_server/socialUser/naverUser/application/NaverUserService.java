package com.positivehotel.nabusi_server.socialUser.naverUser.application;

import com.positivehotel.nabusi_server.socialUser.naverUser.application.dto.NaverUserDto;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface NaverUserService {
    Boolean post(@NonNull NaverUserDto naverUserDto);
    Boolean patch(@NonNull NaverUserDto naverUserDto);
    Boolean patchById(final @NonNull Long id, final @NonNull NaverUserDto naverUserDto);
    Optional<NaverUserDto> getByMobileE164(@NonNull String mobileE164);
    NaverUserDto getByMemberId(Long memberId);
}
