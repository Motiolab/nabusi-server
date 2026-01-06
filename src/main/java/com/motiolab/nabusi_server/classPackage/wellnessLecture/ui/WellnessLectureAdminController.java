package com.motiolab.nabusi_server.classPackage.wellnessLecture.ui;

import com.motiolab.nabusi_server.argumentResolver.MemberId;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.WellnessLectureAdminService;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.WellnessLectureAdminDto;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.request.CreateWellnessLectureListWithWellnessClassAdminRequestV1;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.request.GetWellnessLectureAdminResponseV1;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.request.GetWellnessLectureDetailAdminResponseV1;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.request.UpdateWellnessLectureAdminRequestV1;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class WellnessLectureAdminController {

    private final WellnessLectureAdminService wellnessLectureAdminService;

    @PostMapping("/v1/admin/wellness-lecture/list/{centerId}")
    public ResponseEntity<Boolean> createWellnessLectureListWithWellnessClass(final @MemberId Long memberId,
            final @RequestBody CreateWellnessLectureListWithWellnessClassAdminRequestV1 createWellnessLectureListWithWellnessClassAdminRequestV1) {
        createWellnessLectureListWithWellnessClassAdminRequestV1.setRegistrantId(memberId);
        wellnessLectureAdminService
                .createWellnessLectureListWithWellnessClass(createWellnessLectureListWithWellnessClassAdminRequestV1);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/v1/admin/wellness-lecture/list/{centerId}")
    public ResponseEntity<List<GetWellnessLectureAdminResponseV1>> getWellnessLectureListByStartDate(
            @PathVariable Long centerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        final List<WellnessLectureAdminDto> wellnessLectureAdminDtoList = wellnessLectureAdminService
                .getWellnessLectureListByStartDate(centerId, startDate.atStartOfDay(java.time.ZoneId.systemDefault()));
        final List<GetWellnessLectureAdminResponseV1> wellnessLectureAdminResponseV1List = wellnessLectureAdminDtoList
                .stream()
                .map(wellnessLectureAdminDto -> GetWellnessLectureAdminResponseV1.builder()
                        .id(wellnessLectureAdminDto.getWellnessLectureDto().getId())
                        .name(wellnessLectureAdminDto.getWellnessLectureDto().getName())
                        .maxReservationCnt(wellnessLectureAdminDto.getWellnessLectureDto().getMaxReservationCnt())
                        .room(wellnessLectureAdminDto.getWellnessLectureDto().getRoom())
                        .teacherId(wellnessLectureAdminDto.getWellnessLectureDto().getTeacherId())
                        .teacherName(wellnessLectureAdminDto.getTeacherDto().getName())
                        .wellnessLectureTypeId(
                                wellnessLectureAdminDto.getWellnessLectureDto().getWellnessLectureTypeId())
                        .wellnessLectureTypeName(wellnessLectureAdminDto.getWellnessLectureTypeDto().name())
                        .wellnessLectureTypeDescription(
                                wellnessLectureAdminDto.getWellnessLectureDto().getDescription())
                        .startDateTime(wellnessLectureAdminDto.getWellnessLectureDto().getStartDateTime())
                        .endDateTime(wellnessLectureAdminDto.getWellnessLectureDto().getEndDateTime())
                        .isDelete(wellnessLectureAdminDto.getWellnessLectureDto().getIsDelete())
                        .lectureImageUrlList(wellnessLectureAdminDto.getWellnessLectureDto().getLectureImageUrlList())
                        .build())
                .toList();

        return ResponseEntity.ok(wellnessLectureAdminResponseV1List);
    }

    @GetMapping("/v1/admin/wellness-lecture/detail/{centerId}")
    public ResponseEntity<GetWellnessLectureDetailAdminResponseV1> getWellnessLectureDetailById(
            @PathVariable Long centerId, @RequestParam(defaultValue = "id") Long id) {
        final WellnessLectureAdminDto wellnessLectureAdminDto = wellnessLectureAdminService
                .getWellnessLectureDetailById(id);

        final List<GetWellnessLectureDetailAdminResponseV1.WellnessTicketAvailable> wellnessTicketListList = wellnessLectureAdminDto
                .getWellnessTicketExtensionList()
                .stream()
                .map(wellnessTicketExtension -> GetWellnessLectureDetailAdminResponseV1.WellnessTicketAvailable
                        .builder()
                        .wellnessTicketManagementId(wellnessTicketExtension.getWellnessTicketManagementDto().getId())
                        .wellnessTicketIssuanceName(wellnessTicketExtension.getWellnessTicketManagementDto()
                                .getWellnessTicketIssuanceName())
                        .wellnessTicketId(wellnessTicketExtension.getWellnessTicketDto().getId())
                        .type(wellnessTicketExtension.getWellnessTicketDto().getType())
                        .backgroundColor(wellnessTicketExtension.getWellnessTicketDto().getBackgroundColor())
                        .textColor(wellnessTicketExtension.getWellnessTicketDto().getTextColor())
                        .isDelete(wellnessTicketExtension.getWellnessTicketDto().getIsDelete())
                        .build())
                .toList();

        final GetWellnessLectureDetailAdminResponseV1 getWellnessLectureDetailAdminResponseV1 = GetWellnessLectureDetailAdminResponseV1
                .builder()
                .id(wellnessLectureAdminDto.getWellnessLectureDto().getId())
                .name(wellnessLectureAdminDto.getWellnessLectureDto().getName())
                .description(wellnessLectureAdminDto.getWellnessLectureDto().getDescription())
                .centerId(wellnessLectureAdminDto.getWellnessLectureDto().getCenterId())
                .maxReservationCnt(wellnessLectureAdminDto.getWellnessLectureDto().getMaxReservationCnt())
                .room(wellnessLectureAdminDto.getWellnessLectureDto().getRoom())
                .lectureImageUrlList(wellnessLectureAdminDto.getWellnessLectureDto().getLectureImageUrlList())
                .price(wellnessLectureAdminDto.getWellnessLectureDto().getPrice())
                .teacherId(wellnessLectureAdminDto.getWellnessLectureDto().getTeacherId())
                .teacherName(wellnessLectureAdminDto.getTeacherDto().getName())
                .wellnessLectureTypeId(wellnessLectureAdminDto.getWellnessLectureDto().getWellnessLectureTypeId())
                .wellnessLectureTypeName(wellnessLectureAdminDto.getWellnessLectureTypeDto().name())
                .wellnessLectureTypeDescription(wellnessLectureAdminDto.getWellnessLectureDto().getDescription())
                .startDateTime(wellnessLectureAdminDto.getWellnessLectureDto().getStartDateTime())
                .endDateTime(wellnessLectureAdminDto.getWellnessLectureDto().getEndDateTime())
                .isDelete(wellnessLectureAdminDto.getWellnessLectureDto().getIsDelete())
                .wellnessTicketAvailableList(wellnessTicketListList)
                .build();

        return ResponseEntity.ok(getWellnessLectureDetailAdminResponseV1);
    }

    @DeleteMapping("/v1/admin/wellness-lecture/{centerId}")
    public ResponseEntity<Boolean> deleteWellnessLectureById(@PathVariable Long centerId, @RequestParam Long id,
            @RequestParam Boolean isSendNoti) {
        wellnessLectureAdminService.deleteWellnessLectureById(id, isSendNoti);
        return ResponseEntity.ok(true);
    }

    @PatchMapping("/v1/admin/wellness-lecture/restore/{centerId}")
    public ResponseEntity<Boolean> restoreWellnessLectureById(@PathVariable Long centerId,
            @RequestParam(defaultValue = "id") Long id) {
        wellnessLectureAdminService.restoreWellnessLectureById(id);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/v1/admin/wellness-lecture/{centerId}")
    public ResponseEntity<Boolean> updateWellnessLecture(@PathVariable Long centerId,
            @RequestBody UpdateWellnessLectureAdminRequestV1 updateWellnessLectureAdminRequestV1) {
        updateWellnessLectureAdminRequestV1.setCenterId(centerId);
        wellnessLectureAdminService.updateWellnessLecture(updateWellnessLectureAdminRequestV1);
        return ResponseEntity.ok(true);
    }
}
