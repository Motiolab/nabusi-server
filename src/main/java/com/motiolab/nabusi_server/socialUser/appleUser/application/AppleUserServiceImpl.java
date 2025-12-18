package com.motiolab.nabusi_server.socialUser.appleUser.application;


import com.motiolab.nabusi_server.socialUser.appleUser.application.dto.AppleUserDto;
import com.motiolab.nabusi_server.socialUser.appleUser.domain.AppleUserEntity;
import com.motiolab.nabusi_server.socialUser.appleUser.domain.AppleUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AppleUserServiceImpl implements AppleUserService {
    private final AppleUserRepository appleUserRepository;

    @Override
    public Boolean post(final @NonNull AppleUserDto appleUserDto) {
        final AppleUserEntity appleUserEntity = AppleUserEntity.create(
                appleUserDto.getUserId(),
                appleUserDto.getEmail(),
                appleUserDto.getMobile(),
                appleUserDto.getNickName(),
                appleUserDto.getFullName(),
                appleUserDto.getSub(),
                appleUserDto.getMemberId()
        );

        appleUserRepository.save(appleUserEntity);
        return true;
    }

    @Override
    public Boolean patch(final @NonNull AppleUserDto appleUserDto) {
        return appleUserRepository.findByMobile(appleUserDto.getMobile())
                .map(appleUserEntity -> appleUserEntity.patch(
                        appleUserDto.getUserId(),
                        appleUserDto.getEmail(),
                        appleUserDto.getMobile(),
                        appleUserDto.getNickName(),
                        appleUserDto.getFullName(),
                        appleUserDto.getSub(),
                        appleUserDto.getMemberId()))
                .map(appleUserRepository::save)
                .isPresent();
    }

    @Override
    public Boolean patchById(final @NonNull Long id, final @NonNull AppleUserDto appleUserDto) {
        return appleUserRepository.findById(id)
                .map(appleUserEntity -> appleUserEntity.patch(
                        appleUserDto.getUserId(),
                        appleUserDto.getEmail(),
                        appleUserDto.getMobile(),
                        appleUserDto.getNickName(),
                        appleUserDto.getFullName(),
                        appleUserDto.getSub(),
                        appleUserDto.getMemberId()))
                .map(appleUserRepository::save)
                .isPresent();
    }

    @Override
    public Optional<AppleUserDto> getByMobile(final @NonNull String mobile) {
        return appleUserRepository.findByMobile(mobile)
                .map(appleUserEntity -> AppleUserDto.builder()
                        .id(appleUserEntity.getId())
                        .userId(appleUserEntity.getUserId())
                        .email(appleUserEntity.getEmail())
                        .mobile(appleUserEntity.getMobile())
                        .nickName(appleUserEntity.getNickName())
                        .fullName(appleUserEntity.getFullName())
                        .sub(appleUserEntity.getSub())
                        .memberId(appleUserEntity.getMemberId())
                        .createdDate(appleUserEntity.getCreatedDate().atZone(ZoneId.of("UTC")))
                        .lastUpdatedDate(appleUserEntity.getLastUpdatedDate().atZone(ZoneId.of("UTC")))
                        .build());
    }

    public AppleUserDto getBySub(final @NonNull String sub) {
        return appleUserRepository.findBySub(sub)
                .map(appleUserEntity -> AppleUserDto.builder()
                        .id(appleUserEntity.getId())
                        .userId(appleUserEntity.getUserId())
                        .email(appleUserEntity.getEmail())
                        .mobile(appleUserEntity.getMobile())
                        .nickName(appleUserEntity.getNickName())
                        .fullName(appleUserEntity.getFullName())
                        .sub(appleUserEntity.getSub())
                        .memberId(appleUserEntity.getMemberId())
                        .createdDate(appleUserEntity.getCreatedDate().atZone(ZoneId.of("UTC")))
                        .lastUpdatedDate(appleUserEntity.getLastUpdatedDate().atZone(ZoneId.of("UTC")))
                        .build())
                .orElse(null);
    }

    @Override
    public AppleUserDto getByMemberId(Long memberId) {
        return appleUserRepository.findByMemberId(memberId)
                .map(AppleUserDto::from)
                .orElse(null);
    }
}
