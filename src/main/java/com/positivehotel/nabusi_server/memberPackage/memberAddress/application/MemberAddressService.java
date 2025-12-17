package com.positivehotel.nabusi_server.memberPackage.memberAddress.application;

import com.positivehotel.nabusi_server.memberPackage.memberAddress.application.dto.MemberAddressDto;

import java.util.List;

public interface MemberAddressService {
    List<MemberAddressDto> getAllByMemberId(Long memberId);
    void create(MemberAddressDto memberAddressDto);
    void updateIsDefaultByMemberId(String isDefault, Long memberId);
    void deleteById(Long id);
}
