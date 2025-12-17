package com.positivehotel.nabusi_server.memberPackage.member.application;

import com.positivehotel.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.positivehotel.nabusi_server.memberPackage.member.domain.MemberEntity;
import com.positivehotel.nabusi_server.memberPackage.member.domain.MemberRepository;
import com.positivehotel.nabusi_server.role.application.dto.RoleDto;
import com.positivehotel.nabusi_server.role.domain.RoleEntity;
import com.positivehotel.nabusi_server.role.domain.RoleRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    @Override
    public MemberDto create(@NonNull final MemberDto memberDto) {
        final List<RoleEntity> roleEntityList = roleRepository.findAllByIdIn(memberDto.getRoleList().stream().map(RoleDto::getId).toList());
        final MemberEntity newMemberEntity = MemberEntity.create(memberDto.getName(), memberDto.getEmail(), memberDto.getMobile(), memberDto.getSocialName(), roleEntityList, memberDto.getCenterIdList());
        final MemberEntity savedMemberEntity = memberRepository.save(newMemberEntity);
        return MemberDto.from(savedMemberEntity);
    }

    @Override
    public List<MemberDto> getAll() {
        List<MemberEntity> memberEntities = memberRepository.findAll();

        return memberEntities.stream()
                .map(MemberDto::from)
                .toList();
    }

    @Override
    public MemberDto getById(@NonNull final Long id) {
        return memberRepository.findById(id).map(MemberDto::from).orElse(null);
    }

    @Override
    public MemberDto getByMobileAndSocialName(final @NonNull String mobile, final @NonNull String socialName) {
        return memberRepository.findByMobileAndSocialName(mobile, socialName).map(MemberDto::from).orElse(null);
    }

    @Override
    public MemberDto addRole(@NonNull Long memberId, @NonNull Long roleId) {
        final MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found."));

        final RoleEntity roleEntity = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found."));

        if (!memberEntity.getRoles().contains(roleEntity)) {
            memberEntity.getRoles().add(roleEntity);
            MemberEntity savedMemberEntity = memberRepository.save(memberEntity);
            return MemberDto.from(savedMemberEntity);
        }
        return null;
    }

    @Transactional
    @Override
    public MemberDto addCenterId(Long memberId, Long centerId) {
        final MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found."));

        final List<Long> centerIdList = new ArrayList<>(memberEntity.getCenterIdList());
        centerIdList.add(centerId);

        memberEntity.updateCenterIdList(centerIdList);
        return MemberDto.from(memberEntity);
    }

    @Override
    public List<MemberDto> getAllByCenterIdIn(Long centerId) {
        final List<MemberEntity> memberEntityList = memberRepository.findMembersByCenterId(centerId);
        return memberEntityList.stream()
                .map(MemberDto::from)
                .toList();
    }


    @Override
    public void addRoleListByNameAndCenterId(@NonNull Long memberId, @NonNull Long roleId, @NonNull Long centerId) {
        final MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found."));
        final RoleEntity roleEntity = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found."));
        final List<RoleEntity> roleEntityList = new ArrayList<>(memberEntity.getRoles());
        roleEntityList.add(roleEntity);
        memberEntity.updateRoleList(roleEntityList);
        memberRepository.save(memberEntity);
    }

    @Override
    public void deleteRole(@NonNull Long memberId, @NonNull Long roleId) {
        final MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found."));

        memberEntity.getRoles().removeIf(roleEntity -> roleEntity.getId().equals(roleId));
        memberRepository.save(memberEntity);
    }

    @Override
    public List<MemberDto> getAllByIdList(List<Long> idList) {
        final List<MemberEntity> memberEntityList = memberRepository.findAllById(idList);
        return memberEntityList.stream().map(MemberDto::from).toList();
    }
    @Override
    public List<MemberDto> getAllByRolesId(Long roleId) {
        final List<MemberEntity> memberEntityList = memberRepository.findAllByRolesId(roleId);
        return memberEntityList.stream().map(MemberDto::from).toList();
    }

    @Override
    public void updateMobile(@NonNull Long memberId, @NonNull String mobile) {
        final MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found."));
        memberEntity.updateMobile(mobile);
        memberRepository.save(memberEntity);
    }
}
