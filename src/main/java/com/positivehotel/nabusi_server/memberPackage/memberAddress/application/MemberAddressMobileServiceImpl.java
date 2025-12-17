package com.positivehotel.nabusi_server.memberPackage.memberAddress.application;

import com.positivehotel.nabusi_server.memberPackage.memberAddress.application.dto.MemberAddressDto;
import com.positivehotel.nabusi_server.memberPackage.memberAddress.application.dto.request.CreateMemberAddressMobileRequestV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberAddressMobileServiceImpl implements MemberAddressMobileService {
    private final MemberAddressService memberAddressService;

    @Override
    public void createMemberAddress(CreateMemberAddressMobileRequestV1 createMemberAddressMobileRequestV1) {
        final MemberAddressDto memberAddressDto = MemberAddressDto.builder()
                .memberId(createMemberAddressMobileRequestV1.getMemberId())
                .name(createMemberAddressMobileRequestV1.getName())
                .address(createMemberAddressMobileRequestV1.getAddress())
                .detailAddress(createMemberAddressMobileRequestV1.getDetailAddress())
                .recipient(createMemberAddressMobileRequestV1.getRecipient())
                .mobile(createMemberAddressMobileRequestV1.getMobile())
                .zipCode(createMemberAddressMobileRequestV1.getZipCode())
                .roadName(createMemberAddressMobileRequestV1.getRoadName())
                .isDefault(createMemberAddressMobileRequestV1.getIsDefault())
                .build();

        if(createMemberAddressMobileRequestV1.getIsDefault().equals("Y")) {
            memberAddressService.updateIsDefaultByMemberId("N", createMemberAddressMobileRequestV1.getMemberId());
        }
        memberAddressService.create(memberAddressDto);
    }

    @Override
    public List<MemberAddressDto> getMemberAddressListByMemberId(Long memberId) {
        return memberAddressService.getAllByMemberId(memberId);
    }

    @Override
    public void deleteById(Long id) {
        memberAddressService.deleteById(id);
    }
}
