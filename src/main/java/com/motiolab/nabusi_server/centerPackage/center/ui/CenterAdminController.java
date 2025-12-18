package com.motiolab.nabusi_server.centerPackage.center.ui;

import com.motiolab.nabusi_server.argumentResolver.MemberId;
import com.motiolab.nabusi_server.centerPackage.center.application.CenterAdminService;
import com.motiolab.nabusi_server.centerPackage.center.application.dto.*;
import com.motiolab.nabusi_server.centerPackage.center.application.dto.request.CreateCenterRequestV1;
import com.motiolab.nabusi_server.centerPackage.center.application.dto.request.UpdateCenterInfoRequestV1;
import com.motiolab.nabusi_server.centerPackage.center.application.dto.response.GetCenterInfoResponseV1;
import com.motiolab.nabusi_server.centerPackage.center.application.dto.response.GetMyCenterListByMemberIdResponseV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CenterAdminController {
    private final CenterAdminService centerAdminService;

    @GetMapping("/v1/admin/center/my-center")
    public ResponseEntity<List<GetMyCenterListByMemberIdResponseV1>> getMyCenterListByMemberId(@MemberId Long memberId) {

        final List<CenterAdminDto> centerAdminDtoList = centerAdminService.getMyCenterList(memberId);
        final List<GetMyCenterListByMemberIdResponseV1> getMyCenterListByMemberIdResponseListV1 = centerAdminDtoList.stream()
                .map(centerAdminDto -> GetMyCenterListByMemberIdResponseV1.builder()
                        .id(centerAdminDto.getCenterDto().getId())
                        .name(centerAdminDto.getCenterDto().getName())
                        .address(centerAdminDto.getCenterDto().getAddress())
                        .detailAddress(centerAdminDto.getCenterDto().getDetailAddress())
                        .roadName(centerAdminDto.getCenterDto().getRoadName())
                        .roleName(centerAdminDto.getMyRoleDto().getName())
                        .code(centerAdminDto.getCenterDto().getCode())
                        .isActive(centerAdminDto.getCenterDto().getIsActive())
                        .build()
                )
                .toList();

        return ResponseEntity.ok(getMyCenterListByMemberIdResponseListV1);
    }

    @PostMapping("/v1/admin/center/my-center")
    public ResponseEntity<Boolean> createCenter(@MemberId Long memberId, @RequestBody CreateCenterRequestV1 createCenterRequestV1) {
        final Boolean isCreated = centerAdminService.createCenter(memberId, createCenterRequestV1);
        return ResponseEntity.ok(isCreated);
    }

    @GetMapping("/v1/admin/center/info/{centerId}")
    public ResponseEntity<GetCenterInfoResponseV1> getCenterInfo(@PathVariable Long centerId) {
        final CenterAdminDto centerAdminDto = centerAdminService.getCenterInfo(centerId);

        return getGetCenterInfoResponseV1ResponseEntity(centerAdminDto);
    }

    private ResponseEntity<GetCenterInfoResponseV1> getGetCenterInfoResponseV1ResponseEntity(CenterAdminDto centerAdminDto) {
        final List<GetCenterInfoResponseV1.CenterOpenInfo> centerOpenInfoList = centerAdminDto.getCenterOpenInfoDtoList().stream()
                .map(centerOpenInfoDto -> GetCenterInfoResponseV1.CenterOpenInfo.builder()
                        .id(centerOpenInfoDto.getId())
                        .closeTime(centerOpenInfoDto.getCloseTime())
                        .day(centerOpenInfoDto.getDay())
                        .isDayOff(centerOpenInfoDto.getIsDayOff())
                        .openTime(centerOpenInfoDto.getOpenTime())
                        .build())
                .toList();

        final List<GetCenterInfoResponseV1.CenterRoom> centerRoomList = centerAdminDto.getCenterRoomDtoList().stream()
                .map(centerRoomDto -> GetCenterInfoResponseV1.CenterRoom.builder()
                        .id(centerRoomDto.getId())
                        .name(centerRoomDto.getName())
                        .capacity(centerRoomDto.getCapacity())
                        .build())
                .toList();

        final List<GetCenterInfoResponseV1.CenterContactNumber> centerContactNumberList = centerAdminDto.getCenterContactNumberDtoList().stream()
                .map(centerContactNumberDto -> GetCenterInfoResponseV1.CenterContactNumber.builder()
                        .id(centerContactNumberDto.getId())
                        .contactType(centerContactNumberDto.getContactType())
                        .number(centerContactNumberDto.getNumber())
                        .build())
                .toList();

        final GetCenterInfoResponseV1 getCenterInfoResponseV1 = GetCenterInfoResponseV1.builder()
                .id(centerAdminDto.getCenterDto().getId())
                .address(centerAdminDto.getCenterDto().getAddress())
                .detailAddress(centerAdminDto.getCenterDto().getDetailAddress())
                .roadName(centerAdminDto.getCenterDto().getRoadName())
                .description(centerAdminDto.getCenterDto().getDescription())
                .name(centerAdminDto.getCenterDto().getName())
                .openInfoList(centerOpenInfoList)
                .roomList(centerRoomList)
                .contactNumberList(centerContactNumberList)
                .code(centerAdminDto.getCenterDto().getCode())
                .imageUrlList(centerAdminDto.getCenterDto().getImageUrlList())
                .build();

        return ResponseEntity.ok(getCenterInfoResponseV1);
    }

    @PutMapping("/v1/admin/center/info/{centerId}")
    public ResponseEntity<GetCenterInfoResponseV1> updateCenterInfo(final @RequestBody UpdateCenterInfoRequestV1 updateCenterInfoRequestV1) {
        CenterDto centerDto = CenterDto.builder()
                .id(updateCenterInfoRequestV1.getId())
                .code(updateCenterInfoRequestV1.getCode())
                .address(updateCenterInfoRequestV1.getAddress())
                .detailAddress(updateCenterInfoRequestV1.getDetailAddress())
                .roadName(updateCenterInfoRequestV1.getRoadName())
                .description(updateCenterInfoRequestV1.getDescription())
                .name(updateCenterInfoRequestV1.getCenterName())
                .imageUrlList(updateCenterInfoRequestV1.getImageUrlList())
                .build();

        List<CenterOpenInfoDto> centerOpenInfoDtoList = updateCenterInfoRequestV1.getOpenInfoList().stream()
                .map(openInfo -> CenterOpenInfoDto.builder()
                        .centerId(updateCenterInfoRequestV1.getId())
                        .closeTime(openInfo.getCloseTime())
                        .day(openInfo.getDay())
                        .isDayOff(openInfo.getIsDayOff())
                        .openTime(openInfo.getOpenTime())
                        .build())
                .toList();

        List<CenterRoomDto> centerRoomDtoList = updateCenterInfoRequestV1.getRoomList().stream()
                .map(room -> CenterRoomDto.builder()
                        .centerId(updateCenterInfoRequestV1.getId())
                        .name(room.getName())
                        .build())
                .toList();

        List<CenterContactNumberDto> centerContactNumberDtoList = updateCenterInfoRequestV1.getContactNumberList().stream()
                .map(contactNumber -> CenterContactNumberDto.builder()
                        .centerId(updateCenterInfoRequestV1.getId())
                        .contactType(contactNumber.getContactType())
                        .number(contactNumber.getNumber())
                        .build())
                .toList();


        CenterAdminDto centerAdminDto = CenterAdminDto.builder()
                .centerDto(centerDto)
                .centerOpenInfoDtoList(centerOpenInfoDtoList)
                .centerRoomDtoList(centerRoomDtoList)
                .centerContactNumberDtoList(centerContactNumberDtoList)
                .build();

        CenterAdminDto updatedCenterAdminDto =  centerAdminService.updateCenterInfo(centerAdminDto);
        return getGetCenterInfoResponseV1ResponseEntity(updatedCenterAdminDto);
    }
}