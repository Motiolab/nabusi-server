package com.motiolab.nabusi_server.memberPackage.memberAddress.application;

import com.motiolab.nabusi_server.memberPackage.memberAddress.application.dto.MemberAddressDto;
import com.motiolab.nabusi_server.memberPackage.memberAddress.domain.MemberAddressEntity;
import com.motiolab.nabusi_server.memberPackage.memberAddress.domain.MemberAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberAddressServiceImpl implements MemberAddressService {
    private final MemberAddressRepository memberAddressRepository;

    @Override
    public List<MemberAddressDto> getAllByMemberId(Long memberId) {
        final List<MemberAddressEntity> memberAddressEntityList = memberAddressRepository.findAllByMemberId(memberId);
        return memberAddressEntityList.stream().map(MemberAddressDto::from).toList();
    }

    @Override
    public void create(MemberAddressDto memberAddressDto) {
        final MemberAddressEntity memberAddressEntity = MemberAddressEntity.create(
                memberAddressDto.getMemberId(),
                memberAddressDto.getName(),
                memberAddressDto.getAddress(),
                memberAddressDto.getDetailAddress(),
                memberAddressDto.getRecipient(),
                memberAddressDto.getMobile(),
                memberAddressDto.getZipCode(),
                memberAddressDto.getRoadName(),
                memberAddressDto.getIsDefault()
        );
        memberAddressRepository.save(memberAddressEntity);
    }

    @Override
    public void updateIsDefaultByMemberId(String isDefault, Long memberId) {
        memberAddressRepository.findAllByMemberId(memberId).forEach(memberAddressEntity -> {
            memberAddressEntity.updateIsDefault(isDefault);
            memberAddressRepository.save(memberAddressEntity);
        });
    }

    @Override
    public void deleteById(Long id) {
        memberAddressRepository.deleteById(id);
    }
}
