package com.positivehotel.nabusi_server.memberPackage.member.ui;

import com.positivehotel.nabusi_server.argumentResolver.MemberId;
import com.positivehotel.nabusi_server.memberPackage.member.application.MemberAdminService;
import com.positivehotel.nabusi_server.memberPackage.member.application.dto.MemberAdminDto;
import com.positivehotel.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.positivehotel.nabusi_server.memberPackage.member.application.dto.request.GetMemberToAddTeacherAdminResponseV1;
import com.positivehotel.nabusi_server.memberPackage.member.application.dto.request.UpdateMemberRoleAdminRequestV1;
import com.positivehotel.nabusi_server.memberPackage.member.application.dto.response.GetAllMemberListByCenterIdAdminResponseV1;
import com.positivehotel.nabusi_server.memberPackage.member.application.dto.response.GetMemberDetailByIdAdminResponseV1;
import com.positivehotel.nabusi_server.memberPackage.member.application.dto.response.GetMemberListByCenterIdAdminResponseV1;
import com.positivehotel.nabusi_server.role.application.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class MemberAdminController {
    private final MemberAdminService memberAdminService;

    @GetMapping("/v1/admin/member/role/center-id/not-user/{centerId}")
    public ResponseEntity<List<Long>> getAllMemberRoleCenterIdNotUser(@PathVariable Long centerId, @MemberId Long optionalMemberDto) {
        return Optional.ofNullable(optionalMemberDto)
                .map(memberAdminService::getMemberById)
                .map(MemberAdminDto::getMemberDto)
                .map(MemberDto::getRoleList)
                .map(roleDtoList -> roleDtoList.stream()
                        .filter(roleDto -> !roleDto.getName().equals("USER"))
                        .map(RoleDto::getCenterId)
                        .toList())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));
    }


    @GetMapping("/v1/admin/member/admin/{centerId}")
    public ResponseEntity<List<GetMemberListByCenterIdAdminResponseV1>> getMemberListByCenterId(@PathVariable Long centerId) {
        final List<MemberAdminDto> adminMemberAdminDtoList = memberAdminService.getAdminMemberListByCenterId(centerId);
        final List<GetMemberListByCenterIdAdminResponseV1> getMemberListByCenterIdAdminResponseV1List = adminMemberAdminDtoList.stream()
                .map(memberAdminDto -> {
                    final RoleDto targetRoleDto = memberAdminDto.getMemberDto().getRoleList().stream()
                            .filter(roleDto -> roleDto.getCenterId().equals(centerId))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Role not found"));

                    return GetMemberListByCenterIdAdminResponseV1.builder()
                            .memberId(memberAdminDto.getMemberDto().getId())
                            .name(memberAdminDto.getMemberDto().getName())
                            .email(memberAdminDto.getMemberDto().getEmail())
                            .mobile(memberAdminDto.getMemberDto().getMobile())
                            .roleName(targetRoleDto.getName())
                            .roleId(targetRoleDto.getId())
                            .build();
                })
                .toList();

        return ResponseEntity.ok(getMemberListByCenterIdAdminResponseV1List);
    }


    @PatchMapping("/v1/admin/member/role/{centerId}")
    public ResponseEntity<Boolean> updateMemberRole(@PathVariable Long centerId, @RequestBody UpdateMemberRoleAdminRequestV1 updateMemberRoleAdminRequestV1) {
        final Boolean isSuccess = memberAdminService.updateMemberRole(centerId, updateMemberRoleAdminRequestV1.getMemberId(), updateMemberRoleAdminRequestV1.getRoleId());
        return ResponseEntity.ok(isSuccess);
    }

    @DeleteMapping("/v1/admin/member/role/{centerId}")
    public ResponseEntity<Boolean> deleteAdminRoleByMemberIdList(@PathVariable Long centerId, @RequestBody List<Long> memberIdList) {
        final Boolean isSuccess = memberAdminService.deleteAdminRoleByMemberIdList(centerId, memberIdList);
        return ResponseEntity.ok(isSuccess);
    }

    @PatchMapping("/v1/admin/member/role/owner/pass/{centerId}")
    public ResponseEntity<Boolean> passOwnerRole(@MemberId Long memberId, @PathVariable Long centerId, @RequestBody Long targetMemberId) {
        final Boolean isSuccess = memberAdminService.passOwnerRole(memberId, centerId, targetMemberId);
        return ResponseEntity.ok(isSuccess);
    }

    @GetMapping("/v1/admin/member/list/add/teacher/{centerId}")
    public ResponseEntity<List<GetMemberToAddTeacherAdminResponseV1>> getMemberListToAddTeacherByCenterId(@PathVariable Long centerId) {
        final List<MemberAdminDto> memberAdminDtoList = memberAdminService.getMemberListToAddTeacherByCenterId(centerId);
        final List<GetMemberToAddTeacherAdminResponseV1> getMemberToAddTeacherAdminResponseV1List = memberAdminDtoList.stream()
                .map(memberAdminDto -> {
                    final String roleName = memberAdminDto.getMemberDto().getRoleList().stream()
                            .filter(roleDto -> roleDto.getCenterId().equals(centerId))
                            .map(RoleDto::getName)
                            .collect(Collectors.joining(", "));

                    return new GetMemberToAddTeacherAdminResponseV1(
                            memberAdminDto.getMemberDto().getId(),
                            memberAdminDto.getMemberDto().getName(),
                            memberAdminDto.getMemberDto().getMobile(),
                            roleName
                    );
                }).toList();

        return ResponseEntity.ok(getMemberToAddTeacherAdminResponseV1List);
    }

    @GetMapping("/v1/admin/member/all/{centerId}")
    public ResponseEntity<List<GetAllMemberListByCenterIdAdminResponseV1>> getAllMemberListByCenterId(@PathVariable Long centerId) {
        final List<MemberAdminDto> memberAdminDtoList = memberAdminService.getAllMemberListByCenterId(centerId);

        List<GetAllMemberListByCenterIdAdminResponseV1> getAllMemberListByCenterIdAdminResponseV1List = memberAdminDtoList.stream().map(memberAdminDto -> {
            final String roleName = memberAdminDto.getMemberDto().getRoleList().stream()
                    .filter(roleDto -> roleDto.getCenterId().equals(centerId))
                    .map(RoleDto::getName)
                    .collect(Collectors.joining(", "));

            final List<GetAllMemberListByCenterIdAdminResponseV1.WellnessTicketIssuance> wellnessTicketIssuanceList = memberAdminDto.getWellnessTicketIssuanceDtoList()
                    .stream()
                    .map(wellnessTicketIssuanceDto -> GetAllMemberListByCenterIdAdminResponseV1.WellnessTicketIssuance.builder()
                            .id(wellnessTicketIssuanceDto.getId())
                            .name(wellnessTicketIssuanceDto.getName())
                            .type(wellnessTicketIssuanceDto.getType())
                            .backgroundColor(wellnessTicketIssuanceDto.getBackgroundColor())
                            .textColor(wellnessTicketIssuanceDto.getTextColor())
                            .limitType(wellnessTicketIssuanceDto.getLimitType())
                            .limitCnt(wellnessTicketIssuanceDto.getLimitCnt())
                            .isDelete(wellnessTicketIssuanceDto.getIsDelete())
                            .remainingCnt(wellnessTicketIssuanceDto.getRemainingCnt())
                            .remainingDate(Duration.between(ZonedDateTime.now(), wellnessTicketIssuanceDto.getExpireDate()).toDays())
                            .unpaidValue(wellnessTicketIssuanceDto.getUnpaidValue())
                            .totalUsableCnt(wellnessTicketIssuanceDto.getTotalUsableCnt())
                            .startDate(wellnessTicketIssuanceDto.getStartDate())
                            .build())
                    .toList();

            final List<GetAllMemberListByCenterIdAdminResponseV1.MemberMemo> memberMemoList = memberAdminDto.getMemberMemoExtensionList()
                    .stream()
                    .map(memberMemoExtension -> GetAllMemberListByCenterIdAdminResponseV1.MemberMemo.builder()
                            .id(memberMemoExtension.getMemberMemoDto().getId())
                            .content(memberMemoExtension.getMemberMemoDto().getContent())
                            .build()).toList();

            return GetAllMemberListByCenterIdAdminResponseV1.builder()
                    .id(memberAdminDto.getMemberDto().getId())
                    .name(memberAdminDto.getMemberDto().getName())
                    .mobile(memberAdminDto.getMemberDto().getMobile())
                    .roleName(roleName)
                    .socialName(memberAdminDto.getMemberDto().getSocialName())
                    .wellnessTicketIssuanceList(wellnessTicketIssuanceList)
                    .memberMemoList(memberMemoList)
                    .createdDate(memberAdminDto.getMemberDto().getCreatedDate())
                    .build();
        }).toList();

        return ResponseEntity.ok(getAllMemberListByCenterIdAdminResponseV1List);
    }

    @GetMapping("/v1/admin/member/detail/{centerId}")
    public ResponseEntity<GetMemberDetailByIdAdminResponseV1> getMemberDetailById(@PathVariable Long centerId, @RequestParam(defaultValue = "id") Long id) {
        final MemberAdminDto memberAdminDto = memberAdminService.getMemberDetailById(id);

        final List<GetMemberDetailByIdAdminResponseV1.WellnessTicketIssuance> wellnessTicketIssuanceList = memberAdminDto.getWellnessTicketIssuanceDtoList()
                .stream()
                .map(wellnessTicketIssuanceDto -> GetMemberDetailByIdAdminResponseV1.WellnessTicketIssuance.builder()
                        .id(wellnessTicketIssuanceDto.getId())
                        .name(wellnessTicketIssuanceDto.getName())
                        .type(wellnessTicketIssuanceDto.getType())
                        .backgroundColor(wellnessTicketIssuanceDto.getBackgroundColor())
                        .textColor(wellnessTicketIssuanceDto.getTextColor())
                        .limitType(wellnessTicketIssuanceDto.getLimitType())
                        .limitCnt(wellnessTicketIssuanceDto.getLimitCnt())
                        .isDelete(wellnessTicketIssuanceDto.getIsDelete())
                        .remainingCnt(wellnessTicketIssuanceDto.getRemainingCnt())
                        .remainingDate(Duration.between(ZonedDateTime.now(), wellnessTicketIssuanceDto.getExpireDate()).toDays())
                        .totalUsableCnt(wellnessTicketIssuanceDto.getTotalUsableCnt())
                        .unpaidValue(wellnessTicketIssuanceDto.getUnpaidValue())
                        .startDate(wellnessTicketIssuanceDto.getStartDate())
                        .expireDate(wellnessTicketIssuanceDto.getExpireDate())
                        .build())
                .toList();

        final List<GetMemberDetailByIdAdminResponseV1.MemberMemo> memberMemoList = memberAdminDto.getMemberMemoExtensionList()
                .stream()
                .map(memberMemoExtension -> GetMemberDetailByIdAdminResponseV1.MemberMemo.builder()
                        .id(memberMemoExtension.getMemberMemoDto().getId())
                        .content(memberMemoExtension.getMemberMemoDto().getContent())
                        .registerName(memberMemoExtension.getRegisterMemberDto().getName())
                        .createdDate(memberMemoExtension.getMemberMemoDto().getCreatedDate())
                        .build())
                .toList();

        final GetMemberDetailByIdAdminResponseV1 getMemberDetailByIdAdminResponseV1 = GetMemberDetailByIdAdminResponseV1.builder()
                .id(memberAdminDto.getMemberDto().getId())
                .name(memberAdminDto.getMemberDto().getName())
                .mobile(memberAdminDto.getMemberDto().getMobile())
                .roleName(memberAdminDto.getMemberDto().getRoleList().stream()
                        .filter(roleDto -> roleDto.getCenterId().equals(centerId))
                        .map(RoleDto::getName)
                        .collect(Collectors.joining(", ")))
                .wellnessTicketIssuanceList(wellnessTicketIssuanceList)
                .memberMemoList(memberMemoList.stream().sorted(Comparator.comparing(GetMemberDetailByIdAdminResponseV1.MemberMemo::getCreatedDate).reversed()).toList())
                .createdDate(memberAdminDto.getMemberDto().getCreatedDate())
                .build();

        if(memberAdminDto.getMemberDto().getSocialName().equals("kakao")) {
            String birthYear = memberAdminDto.getKakaoUserDto().getBirthYear();
            String birthMonth = memberAdminDto.getKakaoUserDto().getBirthDay().substring(0, 2);
            String birthDay = memberAdminDto.getKakaoUserDto().getBirthDay().substring(2, 4);
            getMemberDetailByIdAdminResponseV1.setBirthDay(birthYear + "." + birthMonth + "." + birthDay);

            String birthDateString = birthYear + "-" + birthMonth + "-" + birthDay;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birthDate = LocalDate.parse(birthDateString, formatter);
            Integer age = calculateAge(birthDate, LocalDate.now());
            getMemberDetailByIdAdminResponseV1.setAge(age.toString() + "세");
            getMemberDetailByIdAdminResponseV1.setGender(convertGender(memberAdminDto.getKakaoUserDto().getGender()));
            getMemberDetailByIdAdminResponseV1.setEmail(memberAdminDto.getKakaoUserDto().getEmail());
        }else if(memberAdminDto.getMemberDto().getSocialName().equals("naver")) {
            String birthYear = memberAdminDto.getNaverUserDto().getBirthyear();
            String birthMonth = memberAdminDto.getNaverUserDto().getBirthday().substring(0, 2);
            String birthDay = memberAdminDto.getNaverUserDto().getBirthday().substring(3, 5);
            getMemberDetailByIdAdminResponseV1.setBirthDay(birthYear + "." + birthMonth + "." + birthDay);

            String birthDateString = birthYear + "-" + birthMonth + "-" + birthDay;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birthDate = LocalDate.parse(birthDateString, formatter);
            Integer age = calculateAge(birthDate, LocalDate.now());
            getMemberDetailByIdAdminResponseV1.setAge(age.toString() + "세");
            getMemberDetailByIdAdminResponseV1.setGender(convertGender(memberAdminDto.getNaverUserDto().getGender()));
            getMemberDetailByIdAdminResponseV1.setEmail(memberAdminDto.getNaverUserDto().getEmail());
        }

        return ResponseEntity.ok(getMemberDetailByIdAdminResponseV1);
    }

    private Integer calculateAge(LocalDate birthDate, LocalDate currentDate) {
        int age = currentDate.getYear() - birthDate.getYear();
        if (currentDate.isBefore(birthDate.plusYears(age))) {
            age--;
        }
        return age;
    }

    private String convertGender(String gender) {
        if (gender.equals("male") || gender.equals("M")) {
            return "남성";
        } else if (gender.equals("female") || gender.equals("F")) {
            return "여성";
        } else {
            return "알 수 없음";
        }
    }

}
