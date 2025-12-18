package com.motiolab.nabusi_server.socialUser.naverToken.application;

import com.motiolab.nabusi_server.socialUser.naverToken.application.dto.NaverTokenDto;

public interface NaverTokenService {
    NaverTokenDto getByMemberIdAndAccessToken(Long memberId, String accessToken);
    NaverTokenDto getFirstByMemberIdOrderByCreatedDateDesc(Long memberId);
    void create(NaverTokenDto naverTokenDto);
    Boolean patch(NaverTokenDto naverTokenDto);
}
