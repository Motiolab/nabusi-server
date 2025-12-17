package com.positivehotel.nabusi_server.memberPackage.memberAddress.ui;

import com.positivehotel.nabusi_server.argumentResolver.MemberId;
import com.positivehotel.nabusi_server.memberPackage.memberAddress.application.MemberAddressMobileService;
import com.positivehotel.nabusi_server.memberPackage.memberAddress.application.dto.MemberAddressDto;
import com.positivehotel.nabusi_server.memberPackage.memberAddress.application.dto.request.CreateMemberAddressMobileRequestV1;
import com.positivehotel.nabusi_server.memberPackage.memberAddress.application.dto.response.GetMemberAddressMobileResponseV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberAddressMobileController {
    private final MemberAddressMobileService memberAddressMobileService;

    @PostMapping("/v1/admin/member/address")
    public ResponseEntity<Boolean> createMemberAddress(@MemberId Long memberId, @RequestBody CreateMemberAddressMobileRequestV1 createMemberAddressMobileRequestV1) {
        createMemberAddressMobileRequestV1.setMemberId(memberId);
        memberAddressMobileService.createMemberAddress(createMemberAddressMobileRequestV1);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/v1/admin/member/address")
    public ResponseEntity<List<GetMemberAddressMobileResponseV1>> getMemberAddressByMemberId(@MemberId Long memberId) {
        final List<MemberAddressDto> memberAddressDtoList = memberAddressMobileService.getMemberAddressListByMemberId(memberId);
        final List<GetMemberAddressMobileResponseV1> memberAddressMobileResponseV1List = memberAddressDtoList.stream()
                .map(memberAddressDto ->
                        GetMemberAddressMobileResponseV1.builder()
                                .id(memberAddressDto.getId())
                                .memberId(memberAddressDto.getMemberId())
                                .name(memberAddressDto.getName())
                                .address(memberAddressDto.getAddress())
                                .detailAddress(memberAddressDto.getDetailAddress())
                                .recipient(memberAddressDto.getRecipient())
                                .mobile(memberAddressDto.getMobile())
                                .zipCode(memberAddressDto.getZipCode())
                                .roadName(memberAddressDto.getRoadName())
                                .isDefault(memberAddressDto.getIsDefault())
                                .build()
                ).toList();

        return ResponseEntity.ok(memberAddressMobileResponseV1List);
    }

    @DeleteMapping("/v1/admin/member/address/{id}")
    public ResponseEntity<Boolean> deleteMemberAddress(@PathVariable Long id) {
        memberAddressMobileService.deleteById(id);
        return ResponseEntity.ok(true);
    }
}
