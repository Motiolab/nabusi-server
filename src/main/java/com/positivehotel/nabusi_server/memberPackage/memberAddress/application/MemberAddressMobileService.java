package com.positivehotel.nabusi_server.memberPackage.memberAddress.application;

import com.positivehotel.nabusi_server.memberPackage.memberAddress.application.dto.MemberAddressDto;
import com.positivehotel.nabusi_server.memberPackage.memberAddress.application.dto.request.CreateMemberAddressMobileRequestV1;

import java.util.List;

public interface MemberAddressMobileService {
    void createMemberAddress(CreateMemberAddressMobileRequestV1 createMemberAddressMobileRequestV1);
    List<MemberAddressDto> getMemberAddressListByMemberId(Long memberId);
    void deleteById(Long id);
}
