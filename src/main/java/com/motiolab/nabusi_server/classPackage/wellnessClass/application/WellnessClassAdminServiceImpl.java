package com.motiolab.nabusi_server.classPackage.wellnessClass.application;

import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.WellnessClassAdminDto;
import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.WellnessClassDto;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.WellnessLectureService;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.WellnessLectureDto;
import com.motiolab.nabusi_server.classPackage.wellnessLectureType.application.WellnessLectureTypeService;
import com.motiolab.nabusi_server.classPackage.wellnessLectureType.application.dto.WellnessLectureTypeDto;
import com.motiolab.nabusi_server.exception.customException.ExistsAlreadyException;
import com.motiolab.nabusi_server.exception.customException.NotFoundException;
import com.motiolab.nabusi_server.reservation.application.ReservationService;
import com.motiolab.nabusi_server.reservation.application.dto.ReservationDto;
import com.motiolab.nabusi_server.reservation.enums.ReservationStatus;
import com.motiolab.nabusi_server.teacher.application.TeacherService;
import com.motiolab.nabusi_server.teacher.application.dto.TeacherDto;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicket.application.WellnessTicketService;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicket.application.dtos.WellnessTicketDto;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketManagement.application.WellnessTicketManagementService;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketManagement.application.dto.WellnessTicketManagementDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class WellnessClassAdminServiceImpl implements WellnessClassAdminService {
        private final WellnessClassService wellnessClassService;
        private final TeacherService teacherService;
        private final WellnessLectureTypeService wellnessLectureTypeService;
        private final WellnessTicketManagementService wellnessTicketManagementService;
        private final WellnessTicketService wellnessTicketService;
        private final WellnessLectureService wellnessLectureService;
        private final ReservationService reservationService;

        @Override
        public List<WellnessClassAdminDto> getWellnessClassNameListByCenterId(Long centerId) {
                final List<WellnessClassDto> wellnessClassDtoList = wellnessClassService
                                .getAllByCenterIdAndIsDeleteFalse(centerId);
                final List<Long> teacherIdList = wellnessClassDtoList.stream().map(WellnessClassDto::getTeacherId)
                                .distinct().toList();
                final List<TeacherDto> teacherDtoList = teacherService.getAllByIdList(teacherIdList);

                return wellnessClassDtoList.stream()
                                .map(wellnessClassDto -> {
                                        TeacherDto targetTeacherDto = teacherDtoList.stream()
                                                        .filter(teacherDto -> teacherDto.getId()
                                                                        .equals(wellnessClassDto.getTeacherId()))
                                                        .findFirst()
                                                        .orElse(null);

                                        return WellnessClassAdminDto.builder()
                                                        .wellnessClassDto(wellnessClassDto)
                                                        .teacherDto(targetTeacherDto)
                                                        .build();
                                }).toList();
        }

        @Override
        public void createWellnessClassByCenterIdAndName(Long registerId, Long centerId, String wellnessClassName) {
                final WellnessClassDto existsWellnessClassDto = wellnessClassService.getByCenterIdAndName(centerId,
                                wellnessClassName);

                if (existsWellnessClassDto != null) {
                        if (existsWellnessClassDto.getIsDelete()) {
                                // 삭제되었던 데이터면 복구
                                existsWellnessClassDto.setIsDelete(false);
                                existsWellnessClassDto.setRegisterId(registerId);
                                wellnessClassService.update(existsWellnessClassDto);
                                return;
                        }
                        throw new ExistsAlreadyException(WellnessClassDto.class, existsWellnessClassDto.getId());
                }

                final WellnessClassDto newWellnessClassDto = WellnessClassDto.builder()
                                .name(wellnessClassName)
                                .centerId(centerId)
                                .registerId(registerId)
                                .isDelete(false)
                                .build();

                wellnessClassService.create(newWellnessClassDto);
        }

        @Override
        public WellnessClassAdminDto getByIdAndCenterId(Long id, Long centerId) {
                final WellnessClassDto wellnessClassDto = wellnessClassService.getByIdAndCenterId(id, centerId);
                if (wellnessClassDto == null)
                        throw new NotFoundException(WellnessClassDto.class, id);

                return WellnessClassAdminDto.builder()
                                .wellnessClassDto(wellnessClassDto)
                                .build();
        }

        @Override
        public List<WellnessClassAdminDto> getWellnessClassAllByCenterId(Long centerId) {
                final List<WellnessClassDto> wellnessClassDtoList = wellnessClassService
                                .getAllByCenterIdAndIsDeleteFalse(centerId);
                final List<Long> teacherIdList = wellnessClassDtoList.stream().map(WellnessClassDto::getTeacherId)
                                .distinct().toList();
                final List<TeacherDto> teacherDtoList = teacherService.getAllByIdList(teacherIdList);
                final List<Long> wellnessLectureTypeId = wellnessClassDtoList.stream()
                                .map(WellnessClassDto::getWellnessLectureTypeId).distinct().toList();
                final List<WellnessLectureTypeDto> wellnessLectureTypeDtoList = wellnessLectureTypeService
                                .getAllByIdList(wellnessLectureTypeId);
                final List<Long> wellnessTicketManagementIdList = wellnessClassDtoList.stream()
                                .map(WellnessClassDto::getWellnessTicketManagementIdList)
                                .filter(Objects::nonNull)
                                .flatMap(List::stream)
                                .distinct()
                                .toList();

                final List<WellnessTicketManagementDto> wellnessTicketManagementDtoList = wellnessTicketManagementService
                                .getAllByIdList(wellnessTicketManagementIdList);
                final List<Long> wellnessTicketIdList = wellnessTicketManagementDtoList.stream()
                                .map(WellnessTicketManagementDto::getWellnessTicketId).toList();
                final List<WellnessTicketDto> wellnessTicketDtoList = wellnessTicketService
                                .getAllById(wellnessTicketIdList);

                return wellnessClassDtoList.stream()
                                .map(wellnessClassDto -> {
                                        final TeacherDto targetTeacherDto = teacherDtoList.stream()
                                                        .filter(teacherDto -> teacherDto.getId()
                                                                        .equals(wellnessClassDto.getTeacherId()))
                                                        .findFirst()
                                                        .orElse(null);
                                        final WellnessLectureTypeDto wellnessLectureTypeDto = wellnessLectureTypeDtoList
                                                        .stream()
                                                        .filter(wellnessLectureTypeDto1 -> wellnessLectureTypeDto1.id()
                                                                        .equals(wellnessClassDto
                                                                                        .getWellnessLectureTypeId()))
                                                        .findFirst()
                                                        .orElse(null);

                                        final List<WellnessTicketManagementDto> wellnessTicketManagementDtos = wellnessTicketManagementDtoList
                                                        .stream()
                                                        .filter(wellnessTicketManagementDto -> wellnessClassDto
                                                                        .getWellnessTicketManagementIdList()
                                                                        .contains(wellnessTicketManagementDto.getId()))
                                                        .toList();

                                        final List<Long> targetWellnessTicketIdList = wellnessTicketManagementDtos
                                                        .stream().map(WellnessTicketManagementDto::getWellnessTicketId)
                                                        .toList();
                                        final List<WellnessTicketDto> wellnessTicketDtos = wellnessTicketDtoList
                                                        .stream().filter(wellnessTicketDto -> targetWellnessTicketIdList
                                                                        .contains(wellnessTicketDto.getId()))
                                                        .toList();

                                        return WellnessClassAdminDto.builder()
                                                        .wellnessClassDto(wellnessClassDto)
                                                        .teacherDto(targetTeacherDto)
                                                        .wellnessLectureTypeDto(wellnessLectureTypeDto)
                                                        .wellnessTicketDtoList(wellnessTicketDtos)
                                                        .build();
                                }).toList();
        }

        @Override
        public List<WellnessClassAdminDto> getWellnessClassDetailWithLectureByCenterId(Long centerId) {
                final List<WellnessClassDto> wellnessClassDtoList = wellnessClassService
                                .getAllByCenterIdAndIsDeleteFalse(centerId);
                final List<Long> teacherIdList = wellnessClassDtoList.stream().map(WellnessClassDto::getTeacherId)
                                .distinct().toList();
                final List<TeacherDto> teacherDtoList = teacherService.getAllByIdList(teacherIdList);
                final List<Long> wellnessLectureTypeId = wellnessClassDtoList.stream()
                                .map(WellnessClassDto::getWellnessLectureTypeId).distinct().toList();
                final List<WellnessLectureTypeDto> wellnessLectureTypeDtoList = wellnessLectureTypeService
                                .getAllByIdList(wellnessLectureTypeId);
                final List<Long> wellnessTicketManagementIdList = wellnessClassDtoList.stream()
                                .map(WellnessClassDto::getWellnessTicketManagementIdList)
                                .filter(Objects::nonNull)
                                .flatMap(List::stream)
                                .distinct()
                                .toList();

                final List<WellnessTicketManagementDto> wellnessTicketManagementDtoList = wellnessTicketManagementService
                                .getAllByIdList(wellnessTicketManagementIdList);
                final List<Long> wellnessTicketIdList = wellnessTicketManagementDtoList.stream()
                                .map(WellnessTicketManagementDto::getWellnessTicketId).toList();
                final List<WellnessTicketDto> wellnessTicketDtoList = wellnessTicketService
                                .getAllById(wellnessTicketIdList);

                final List<Long> wellnessClassIdList = wellnessClassDtoList.stream().map(WellnessClassDto::getId)
                                .toList();
                final List<WellnessLectureDto> wellnessLectureDtoList = wellnessLectureService
                                .getAllByWellnessClassIdList(wellnessClassIdList);
                final List<Long> wellnessLectureIdList = wellnessLectureDtoList.stream().map(WellnessLectureDto::getId)
                                .toList();
                final List<ReservationDto> reservationDtoList = reservationService
                                .getAllByWellnessLectureIdList(wellnessLectureIdList);

                return wellnessClassDtoList.stream()
                                .map(wellnessClassDto -> {
                                        final TeacherDto targetTeacherDto = teacherDtoList.stream()
                                                        .filter(teacherDto -> teacherDto.getId()
                                                                        .equals(wellnessClassDto.getTeacherId()))
                                                        .findFirst()
                                                        .orElse(null);

                                        final WellnessLectureTypeDto wellnessLectureTypeDto = wellnessLectureTypeDtoList
                                                        .stream()
                                                        .filter(wellnessLectureTypeDto1 -> wellnessLectureTypeDto1.id()
                                                                        .equals(wellnessClassDto
                                                                                        .getWellnessLectureTypeId()))
                                                        .findFirst()
                                                        .orElse(null);

                                        final List<WellnessTicketManagementDto> wellnessTicketManagementDtos = wellnessTicketManagementDtoList
                                                        .stream()
                                                        .filter(wellnessTicketManagementDto -> wellnessClassDto
                                                                        .getWellnessTicketManagementIdList()
                                                                        .contains(wellnessTicketManagementDto.getId()))
                                                        .toList();

                                        final List<Long> targetWellnessTicketIdList = wellnessTicketManagementDtos
                                                        .stream().map(WellnessTicketManagementDto::getWellnessTicketId)
                                                        .toList();
                                        final List<WellnessTicketDto> wellnessTicketDtos = wellnessTicketDtoList
                                                        .stream().filter(wellnessTicketDto -> targetWellnessTicketIdList
                                                                        .contains(wellnessTicketDto.getId()))
                                                        .toList();
                                        final List<WellnessLectureDto> targetWellnessLectureDtoList = wellnessLectureDtoList
                                                        .stream()
                                                        .filter(wellnessLectureDto -> wellnessLectureDto
                                                                        .getWellnessClassId()
                                                                        .equals(wellnessClassDto.getId()))
                                                        .toList();

                                        final List<WellnessClassAdminDto.WellnessLectureExtension> wellnessLectureExtensionList = targetWellnessLectureDtoList
                                                        .stream()
                                                        .map(wellnessLectureDto -> {
                                                                final List<ReservationDto> reservationDtos = reservationDtoList
                                                                                .stream()
                                                                                .filter(reservationDto -> reservationDto
                                                                                                .getWellnessLectureId()
                                                                                                .equals(wellnessLectureDto
                                                                                                                .getId()))
                                                                                .toList();
                                                                return WellnessClassAdminDto.WellnessLectureExtension
                                                                                .builder()
                                                                                .wellnessLectureDto(wellnessLectureDto)
                                                                                .reservationDtoList(reservationDtos)
                                                                                .build();
                                                        }).toList();

                                        return WellnessClassAdminDto.builder()
                                                        .wellnessClassDto(wellnessClassDto)
                                                        .teacherDto(targetTeacherDto)
                                                        .wellnessLectureTypeDto(wellnessLectureTypeDto)
                                                        .wellnessTicketDtoList(wellnessTicketDtos)
                                                        .wellnessLectureExtensionList(wellnessLectureExtensionList)
                                                        .build();
                                }).toList();
        }

        @Transactional
        @Override
        public void deleteWellnessClassById(Long id, Long centerId) {
                final WellnessClassDto wellnessClassDto = wellnessClassService.getByIdAndCenterId(id, centerId);
                if (wellnessClassDto == null) {
                        throw new NotFoundException(WellnessClassDto.class, id);
                }

                final List<WellnessLectureDto> wellnessLectureDtoList = wellnessLectureService
                                .getAllByWellnessClassIdList(List.of(id));
                final List<Long> lectureIdList = wellnessLectureDtoList.stream().map(WellnessLectureDto::getId)
                                .toList();

                final List<ReservationDto> reservationDtoList = reservationService
                                .getAllByWellnessLectureIdList(lectureIdList);

                final boolean hasActiveReservation = reservationDtoList.stream()
                                .anyMatch(reservationDto -> !List.of(
                                                ReservationStatus.MEMBER_CANCELED_RESERVATION,
                                                ReservationStatus.MEMBER_CANCELED_RESERVATION_REFUND,
                                                ReservationStatus.ADMIN_CANCELED_RESERVATION)
                                                .contains(reservationDto.getStatus()));

                if (hasActiveReservation) {
                        throw new RuntimeException(
                                        "Error: Cannot delete class with active reservations in its lectures.");
                }

                // 수업 삭제
                wellnessClassDto.setIsDelete(true);
                wellnessClassService.update(wellnessClassDto);

                // 종속된 강의 삭제 (이미 지난 수업, 이미 삭제된 수업 제외)
                final ZonedDateTime now = ZonedDateTime.now();
                wellnessLectureDtoList.stream()
                                .filter(lecture -> !lecture.getIsDelete() && lecture.getStartDateTime().isAfter(now))
                                .forEach(lecture -> {
                                        lecture.setIsDelete(true);
                                        wellnessLectureService.update(lecture);
                                });
        }
}
