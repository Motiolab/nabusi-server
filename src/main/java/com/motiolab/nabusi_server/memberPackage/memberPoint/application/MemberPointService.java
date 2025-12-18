package com.motiolab.nabusi_server.memberPackage.memberPoint.application;

import com.motiolab.nabusi_server.memberPackage.memberPoint.application.dto.MemberPointDto;

public interface MemberPointService {
    void addPoint(Long memberId, Long point);
    MemberPointDto getByMemberId(Long memberId);
}
