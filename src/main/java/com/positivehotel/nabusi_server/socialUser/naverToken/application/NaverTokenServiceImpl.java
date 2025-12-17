package com.positivehotel.nabusi_server.socialUser.naverToken.application;

import com.positivehotel.nabusi_server.socialUser.naverToken.application.dto.NaverTokenDto;
import com.positivehotel.nabusi_server.socialUser.naverToken.domain.NaverTokenEntity;
import com.positivehotel.nabusi_server.socialUser.naverToken.domain.NaverTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log
public class NaverTokenServiceImpl implements NaverTokenService {
    private final NaverTokenRepository naverTokenRepository;

    @Override
    public NaverTokenDto getByMemberIdAndAccessToken(Long memberId, String accessToken) {
        return naverTokenRepository.findByMemberIdAndAccessToken(memberId, accessToken)
                .map(NaverTokenDto::from)
                .orElse(null);
    }

    @Override
    public NaverTokenDto getFirstByMemberIdOrderByCreatedDateDesc(Long memberId) {
        return naverTokenRepository.findFirstByMemberIdOrderByCreatedDateDesc(memberId)
                .map(NaverTokenDto::from)
                .orElse(null);
    }

    @Override
    public void create(NaverTokenDto naverTokenDto) {
        final NaverTokenEntity naverTokenEntity = NaverTokenEntity.create(
                naverTokenDto.getAccessToken(),
                naverTokenDto.getExpiresAt(),
                naverTokenDto.getRefreshToken(),
                naverTokenDto.getIssuedAt(),
                naverTokenDto.getMemberId()
        );
        naverTokenRepository.save(naverTokenEntity);
    }

    @Override
    public Boolean patch(NaverTokenDto naverTokenDto) {
        return naverTokenRepository.findById(naverTokenDto.getId())
                .map(naverTokenEntity -> naverTokenEntity.patch(
                        naverTokenDto.getAccessToken(),
                        naverTokenDto.getExpiresAt(),
                        naverTokenDto.getRefreshToken(),
                        naverTokenDto.getIssuedAt(),
                        naverTokenDto.getMemberId()))
                .map(naverTokenRepository::save)
                .isPresent();
    }
}
