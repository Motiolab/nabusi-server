package com.motiolab.nabusi_server.memberPackage.member.application;

import com.motiolab.nabusi_server.memberPackage.member.application.dto.MemberDto;
import lombok.NonNull;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

public interface MemberService {
    MemberDto create(@NonNull MemberDto memberDto);
    List<MemberDto> getAll();
    MemberDto getById(@NonNull Long id);
    MemberDto getByMobileAndSocialName(@NonNull String mobile, @NonNull String socialName);
    MemberDto addRole(@NonNull Long memberId, @NonNull Long roleId);
    MemberDto addCenterId(@NotNull Long memberId, @NotNull Long centerId);
    List<MemberDto> getAllByCenterIdIn(@NotNull Long centerId);
    void addRoleListByNameAndCenterId(@NonNull Long memberId, @NonNull Long roleId, @NonNull Long centerId);
    void deleteRole(@NonNull Long memberId, @NonNull Long roleId);
    List<MemberDto> getAllByIdList(@NotNull List<Long> idList);
    List<MemberDto> getAllByRolesId(@NonNull Long roleId);
    void updateMobile(@NonNull Long memberId, @NonNull String mobile);
}
