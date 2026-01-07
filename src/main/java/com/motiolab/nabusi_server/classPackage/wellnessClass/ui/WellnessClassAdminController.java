package com.motiolab.nabusi_server.classPackage.wellnessClass.ui;

import com.motiolab.nabusi_server.argumentResolver.MemberId;
import com.motiolab.nabusi_server.classPackage.wellnessClass.application.WellnessClassAdminService;
import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.WellnessClassAdminDto;
import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.WellnessClassDto;
import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.request.CreateWellnessClassByCenterIdAdminRequestV1;
import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.response.GetWellnessClassAllAdminResponseV1;
import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.response.GetWellnessClassDetailAdminResponseV1;
import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.response.GetWellnessClassDetailWithLectureAdminResponseV1;
import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.response.GetWellnessClassNameByCenterIdAdminResponseV1;
import com.motiolab.nabusi_server.classPackage.wellnessLectureType.application.dto.WellnessLectureTypeDto;
import com.motiolab.nabusi_server.exception.customException.NotFoundException;
import com.motiolab.nabusi_server.reservation.enums.ReservationStatus;
import com.motiolab.nabusi_server.teacher.application.dto.TeacherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class WellnessClassAdminController {
        private final WellnessClassAdminService wellnessClassAdminService;

        @GetMapping("/v1/admin/wellness-class/name/{centerId}")
        public ResponseEntity<List<GetWellnessClassNameByCenterIdAdminResponseV1>> getWellnessClassNameListByCenterId(
                        @PathVariable Long centerId) {
                final List<WellnessClassAdminDto> wellnessClassAdminDtoList = wellnessClassAdminService
                                .getWellnessClassNameListByCenterId(centerId);

                final List<GetWellnessClassNameByCenterIdAdminResponseV1> getWellnessClassByCenterIdAdminResponseV1List = wellnessClassAdminDtoList
                                .stream()
                                .map(wellnessClassAdminDto -> {
                                        final String teacherName = Optional
                                                        .ofNullable(wellnessClassAdminDto.getTeacherDto())
                                                        .map(TeacherDto::getName)
                                                        .orElse("");

                                        return GetWellnessClassNameByCenterIdAdminResponseV1.builder()
                                                        .id(wellnessClassAdminDto.getWellnessClassDto().getId())
                                                        .name(wellnessClassAdminDto.getWellnessClassDto().getName())
                                                        .teacherName(teacherName)
                                                        .build();
                                })
                                .toList();

                return ResponseEntity.ok(getWellnessClassByCenterIdAdminResponseV1List);
        }

        @PostMapping("/v1/admin/wellness-class/{centerId}")
        public ResponseEntity<Boolean> createWellnessClassByCenterId(@MemberId Long memberId,
                        @PathVariable Long centerId,
                        @RequestBody CreateWellnessClassByCenterIdAdminRequestV1 createWellnessClassByCenterIdAdminRequestV1) {
                wellnessClassAdminService.createWellnessClassByCenterIdAndName(memberId, centerId,
                                createWellnessClassByCenterIdAdminRequestV1.wellnessClassName());
                return ResponseEntity.ok(true);
        }

        @GetMapping("/v1/admin/wellness-class/detail/{centerId}")
        public ResponseEntity<GetWellnessClassDetailAdminResponseV1> getWellnessClassDetailByCenterId(
                        @PathVariable Long centerId, @RequestParam(defaultValue = "id") Long id) {
                final WellnessClassAdminDto wellnessClassAdminDto = wellnessClassAdminService.getByIdAndCenterId(id,
                                centerId);
                final GetWellnessClassDetailAdminResponseV1 getWellnessClassDetailAdminResponseV1 = new GetWellnessClassDetailAdminResponseV1(
                                wellnessClassAdminDto.getWellnessClassDto().getId(),
                                wellnessClassAdminDto.getWellnessClassDto().getName(),
                                wellnessClassAdminDto.getWellnessClassDto().getDescription(),
                                wellnessClassAdminDto.getWellnessClassDto().getCenterId(),
                                wellnessClassAdminDto.getWellnessClassDto().getMaxReservationCnt(),
                                wellnessClassAdminDto.getWellnessClassDto().getRegisterId(),
                                wellnessClassAdminDto.getWellnessClassDto().getRoom(),
                                wellnessClassAdminDto.getWellnessClassDto().getClassImageUrlList(),
                                wellnessClassAdminDto.getWellnessClassDto().getTeacherId(),
                                wellnessClassAdminDto.getWellnessClassDto().getWellnessLectureTypeId(),
                                wellnessClassAdminDto.getWellnessClassDto().getWellnessTicketManagementIdList());

                return ResponseEntity.ok(getWellnessClassDetailAdminResponseV1);
        }

        @GetMapping("/v1/admin/wellness-class/all/{centerId}")
        public ResponseEntity<List<GetWellnessClassAllAdminResponseV1>> getWellnessClassAllByCenterId(
                        @PathVariable Long centerId) {
                final List<WellnessClassAdminDto> wellnessClassAdminDtoList = wellnessClassAdminService
                                .getWellnessClassAllByCenterId(centerId);
                final List<GetWellnessClassAllAdminResponseV1> getWellnessClassAllAdminResponseV1List = wellnessClassAdminDtoList
                                .stream().map(wellnessClassAdminDto -> {
                                        final List<GetWellnessClassAllAdminResponseV1.Ticket> ticketList = wellnessClassAdminDto
                                                        .getWellnessTicketDtoList()
                                                        .stream()
                                                        .map(wellnessTicketDto -> GetWellnessClassAllAdminResponseV1.Ticket
                                                                        .builder()
                                                                        .name(wellnessTicketDto.getName())
                                                                        .type(wellnessTicketDto.getType())
                                                                        .backgroundColor(wellnessTicketDto
                                                                                        .getBackgroundColor())
                                                                        .textColor(wellnessTicketDto.getTextColor())
                                                                        .build())
                                                        .toList();

                                        return GetWellnessClassAllAdminResponseV1.builder()
                                                        .id(wellnessClassAdminDto.getWellnessClassDto().getId())
                                                        .name(wellnessClassAdminDto.getWellnessClassDto().getName())
                                                        .description(wellnessClassAdminDto.getWellnessClassDto()
                                                                        .getDescription())
                                                        .teacherName(wellnessClassAdminDto.getTeacherDto() != null
                                                                        ? wellnessClassAdminDto.getTeacherDto()
                                                                                        .getName()
                                                                        : null)
                                                        .room(wellnessClassAdminDto.getWellnessClassDto().getRoom())
                                                        .type(wellnessClassAdminDto.getWellnessLectureTypeDto() != null
                                                                        ? wellnessClassAdminDto
                                                                                        .getWellnessLectureTypeDto()
                                                                                        .name()
                                                                        : null)
                                                        .ticketList(ticketList)
                                                        .build();
                                }).toList();

                return ResponseEntity.ok(getWellnessClassAllAdminResponseV1List);
        }

        @GetMapping("/v1/admin/wellness-class/detail/with-lecture/{centerId}")
        public ResponseEntity<GetWellnessClassDetailWithLectureAdminResponseV1> getWellnessClassDetailWithLectureByCenterId(
                        @PathVariable Long centerId, @RequestParam Long id) {
                final List<WellnessClassAdminDto> wellnessClassAdminDtoList = wellnessClassAdminService
                                .getWellnessClassDetailWithLectureByCenterId(centerId);

                final WellnessClassAdminDto targetWellnessClassAdminDto = wellnessClassAdminDtoList.stream()
                                .filter(wellnessClassAdminDto -> wellnessClassAdminDto.getWellnessClassDto().getId()
                                                .equals(id))
                                .findFirst()
                                .orElseThrow(() -> new NotFoundException(WellnessClassDto.class, id));

                final ZonedDateTime now = ZonedDateTime.now();

                final List<GetWellnessClassDetailWithLectureAdminResponseV1.Ticket> ticketList = targetWellnessClassAdminDto
                                .getWellnessTicketDtoList().stream()
                                .map(wellnessTicketDto -> GetWellnessClassDetailWithLectureAdminResponseV1.Ticket
                                                .builder()
                                                .name(wellnessTicketDto.getName())
                                                .type(wellnessTicketDto.getType())
                                                .backgroundColor(wellnessTicketDto.getBackgroundColor())
                                                .textColor(wellnessTicketDto.getTextColor())
                                                .build())
                                .toList();

                final List<GetWellnessClassDetailWithLectureAdminResponseV1.Lecture> lectureList = targetWellnessClassAdminDto
                                .getWellnessLectureExtensionList().stream()
                                .map(extension -> GetWellnessClassDetailWithLectureAdminResponseV1.Lecture.builder()
                                                .id(extension.getWellnessLectureDto().getId())
                                                .name(extension.getWellnessLectureDto().getName())
                                                .startDateTime(extension.getWellnessLectureDto().getStartDateTime())
                                                .endDateTime(extension.getWellnessLectureDto().getEndDateTime())
                                                .maxReservationCnt(extension.getWellnessLectureDto()
                                                                .getMaxReservationCnt())
                                                .currentReservationCnt((int) extension.getReservationDtoList().stream()
                                                                .filter(reservationDto -> !List.of(
                                                                                ReservationStatus.MEMBER_CANCELED_RESERVATION,
                                                                                ReservationStatus.MEMBER_CANCELED_RESERVATION_REFUND,
                                                                                ReservationStatus.ADMIN_CANCELED_RESERVATION)
                                                                                .contains(reservationDto.getStatus()))
                                                                .count())
                                                .isDelete(extension.getWellnessLectureDto().getIsDelete())
                                                .isPast(extension.getWellnessLectureDto().getEndDateTime()
                                                                .isBefore(now))
                                                .build())
                                .toList();

                final long pastClassesCount = targetWellnessClassAdminDto.getWellnessLectureExtensionList().stream()
                                .filter(extension -> extension.getWellnessLectureDto().getEndDateTime().isBefore(now))
                                .count();

                final int upcomingClassesCount = (int) targetWellnessClassAdminDto.getWellnessLectureExtensionList()
                                .stream()
                                .filter(extension -> extension.getWellnessLectureDto().getStartDateTime().isAfter(now))
                                .count();

                final GetWellnessClassDetailWithLectureAdminResponseV1 response = GetWellnessClassDetailWithLectureAdminResponseV1
                                .builder()
                                .id(targetWellnessClassAdminDto.getWellnessClassDto().getId())
                                .wellnessClassName(targetWellnessClassAdminDto.getWellnessClassDto().getName())
                                .wellnessClassDescription(targetWellnessClassAdminDto.getWellnessClassDto()
                                                .getDescription())
                                .teacherName(Optional.ofNullable(targetWellnessClassAdminDto.getTeacherDto())
                                                .map(TeacherDto::getName).orElse(null))
                                .teacherImageUrl(Optional.ofNullable(targetWellnessClassAdminDto.getTeacherDto())
                                                .map(TeacherDto::getImageUrl).orElse(null))
                                .room(targetWellnessClassAdminDto.getWellnessClassDto().getRoom())
                                .lectureType(Optional
                                                .ofNullable(targetWellnessClassAdminDto.getWellnessLectureTypeDto())
                                                .map(WellnessLectureTypeDto::name).orElse(null))
                                .pastClassesCount(String.valueOf(pastClassesCount))
                                .upcomingClassesCount(upcomingClassesCount)
                                .ticketList(ticketList)
                                .wellnessClassImageUrlList(targetWellnessClassAdminDto.getWellnessClassDto()
                                                .getClassImageUrlList())
                                .lectureList(lectureList)
                                .build();

                return ResponseEntity.ok(response);
        }

        @DeleteMapping("/v1/admin/wellness-class/{centerId}")
        public ResponseEntity<Boolean> deleteWellnessClassById(@PathVariable Long centerId, @RequestParam Long id) {
                wellnessClassAdminService.deleteWellnessClassById(id, centerId);
                return ResponseEntity.ok(true);
        }

}
