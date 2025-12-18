package com.motiolab.nabusi_server.socialUser.appleUser.application;

import com.motiolab.nabusi_server.socialUser.appleUser.application.dto.AppleUserDto;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface AppleUserService {
    Boolean post(@NonNull AppleUserDto naverUserDto);
    Boolean patch(@NonNull AppleUserDto naverUserDto);
    Boolean patchById(final @NonNull Long id, final @NonNull AppleUserDto appleUserDto);
    Optional<AppleUserDto> getByMobile(@NonNull String mobile);
    AppleUserDto getBySub(@NonNull String sub);
    AppleUserDto getByMemberId(Long memberId);
}
