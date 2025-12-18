package com.motiolab.nabusi_server.classPackage.wellnessLecture.application;

import com.motiolab.nabusi_server.centerPackage.center.application.CenterContactNumberService;
import com.motiolab.nabusi_server.centerPackage.center.application.CenterService;
import com.motiolab.nabusi_server.centerPackage.center.application.dto.CenterContactNumberDto;
import com.motiolab.nabusi_server.centerPackage.center.application.dto.CenterDto;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.WellnessLectureDto;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.WellnessLectureMobileDto;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.WellnessLectureReviewService;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.WellnessLectureReviewDto;
import com.motiolab.nabusi_server.classPackage.wellnessLectureType.application.WellnessLectureTypeService;
import com.motiolab.nabusi_server.classPackage.wellnessLectureType.application.dto.WellnessLectureTypeDto;
import com.motiolab.nabusi_server.memberPackage.member.application.MemberService;
import com.motiolab.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.motiolab.nabusi_server.reservation.application.ReservationService;
import com.motiolab.nabusi_server.reservation.application.dto.ReservationDto;
import com.motiolab.nabusi_server.reservation.enums.ReservationStatus;
import com.motiolab.nabusi_server.teacher.application.TeacherService;
import com.motiolab.nabusi_server.teacher.application.dto.TeacherDto;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application.WellnessTicketIssuanceService;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.WellnessTicketIssuanceDto;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketManagement.application.WellnessTicketManagementService;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketManagement.application.dto.WellnessTicketManagementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class WellnessLectureMobileServiceImpl implements WellnessLectureMobileService{
    private final WellnessLectureService wellnessLectureService;
    private final WellnessLectureTypeService wellnessLectureTypeService;
    private final TeacherService teacherService;
    private final ReservationService reservationService;
    private final WellnessLectureReviewService wellnessLectureReviewService;
    private final CenterService centerService;
    private final CenterContactNumberService centerContactNumberService;
    private final WellnessTicketIssuanceService wellnessTicketIssuanceService;
    private final WellnessTicketManagementService wellnessTicketManagementService;
    private final MemberService memberService;

    @Override
    public List<WellnessLectureMobileDto> getAllWellnessLectureListByCenterIdAndDate(Long memberId, Long centerId, ZonedDateTime startDateTime, ZonedDateTime endDateTime) {
        final List<WellnessLectureDto> wellnessLectureDtoList = wellnessLectureService.getAllByCenterIdAndStartDateTimeBetweenAndIsDeleteFalse(centerId, startDateTime, endDateTime);
        final List<Long> wellnessLectureTypeIdList = wellnessLectureDtoList.stream()
                .map(WellnessLectureDto::getWellnessLectureTypeId)
                .toList();
        final List<WellnessLectureTypeDto> wellnessLectureTypeDtoList = wellnessLectureTypeService.getAllByIdList(wellnessLectureTypeIdList);
        final List<Long> teacherIdList = wellnessLectureDtoList.stream()
                .map(WellnessLectureDto::getTeacherId)
                .toList();
        final List<TeacherDto> teacherDtoList = teacherService.getAllByIdList(teacherIdList);

        final List<Long> wellnessLectureIdList = wellnessLectureDtoList.stream().map(WellnessLectureDto::getId).toList();
        final List<ReservationDto> reservationDtoList = reservationService.getAllByWellnessLectureIdList(wellnessLectureIdList);
        final List<WellnessLectureReviewDto> wellnessLectureReviewDtoList = wellnessLectureReviewService.getAllByWellnessLectureIdList(wellnessLectureIdList);

        return wellnessLectureDtoList.stream().map(wellnessLectureDto -> {
            final TeacherDto targetTeacherDto = teacherDtoList.stream()
                    .filter(teacherDto -> teacherDto.getId().equals(wellnessLectureDto.getTeacherId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Error: Teacher is not found. id: " + wellnessLectureDto.getTeacherId()));

            final WellnessLectureTypeDto targetWellnessLectureTypeDto = wellnessLectureTypeDtoList.stream()
                    .filter(wellnessLectureTypeDto -> wellnessLectureTypeDto.id().equals(wellnessLectureDto.getWellnessLectureTypeId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Error: WellnessLectureType is not found. id: " + wellnessLectureDto.getWellnessLectureTypeId()));

            // 최신 예약을 먼저 정렬
            final ReservationDto myReservation = memberId == null ? null : reservationDtoList.stream()
                    .filter(reservationDto -> reservationDto.getMemberId().equals(memberId) && reservationDto.getWellnessLectureId().equals(wellnessLectureDto.getId()))
                    .min((r1, r2) -> r2.getCreatedDate().compareTo(r1.getCreatedDate()))
                    .orElse(null);


            final WellnessLectureReviewDto myWellnessLectureReviewDto = myReservation == null ? null : wellnessLectureReviewDtoList.stream()
                    .filter(wellnessLectureReviewDto -> wellnessLectureReviewDto.getMemberId().equals(memberId) && wellnessLectureReviewDto.getWellnessLectureId().equals(wellnessLectureDto.getId()))
                    .findFirst()
                    .orElse(null);

            return WellnessLectureMobileDto.builder()
                    .wellnessLectureDto(wellnessLectureDto)
                    .teacherDto(targetTeacherDto)
                    .wellnessLectureTypeDto(targetWellnessLectureTypeDto)
                    .myReservationDto(myReservation)
                    .myWellnessLectureReviewDto(myWellnessLectureReviewDto)
                    .reservationExtensionList(reservationDtoList
                            .stream()
                            .filter(reservationDto -> !(reservationDto.getStatus().equals(ReservationStatus.MEMBER_CANCELED_RESERVATION) || reservationDto.getStatus().equals(ReservationStatus.ADMIN_CANCELED_RESERVATION)))
                            .map(reservationDto -> WellnessLectureMobileDto.ReservationExtension.builder().reservationDto(reservationDto).build())
                            .toList()
                    )
                    .build();
        }).toList();

    }

    @Override
    public List<WellnessLectureMobileDto> getAllWellnessLectureListByCenterId(Long memberId, Long centerId) {
        final List<WellnessLectureDto> wellnessLectureDtoList = wellnessLectureService.getAllByCenterIdAndIsDeleteFalse(centerId);
        final List<Long> wellnessLectureIdList = wellnessLectureDtoList.stream().map(WellnessLectureDto::getId).distinct().toList();
        final List<ReservationDto> reservationDtoList = reservationService.getAllByWellnessLectureIdList(wellnessLectureIdList);
        return wellnessLectureDtoList
                .stream()
                .map(wellnessLectureDto -> {
                    final List<ReservationDto> targetReservationDtoList = reservationDtoList
                            .stream()
                            .filter(reservationDto -> !reservationDto.getStatus().equals(ReservationStatus.ADMIN_CANCELED_RESERVATION))
                            .filter(reservationDto -> !reservationDto.getStatus().equals(ReservationStatus.MEMBER_CANCELED_RESERVATION))
                            .filter(reservationDto -> reservationDto.getWellnessLectureId().equals(wellnessLectureDto.getId()))
                            .toList();

                    final List<WellnessLectureMobileDto.ReservationExtension> reservationExtensionList = targetReservationDtoList.stream().map(reservationDto -> WellnessLectureMobileDto.ReservationExtension.builder().reservationDto(reservationDto).build()).toList();

                    return WellnessLectureMobileDto.builder()
                            .wellnessLectureDto(wellnessLectureDto)
                            .reservationExtensionList(reservationExtensionList)
                            .build();
                })
                .toList();
    }

    @Override
    public WellnessLectureMobileDto getWellnessLectureDetailByWellnessLectureId(Long memberId, Long wellnessLectureId) {
        final WellnessLectureDto wellnessLectureDto = wellnessLectureService.getById(wellnessLectureId);
        final WellnessLectureTypeDto wellnessLectureTypeDto = wellnessLectureTypeService.getById(wellnessLectureDto.getWellnessLectureTypeId());
        final TeacherDto teacherDto = teacherService.getById(wellnessLectureDto.getTeacherId());
        final CenterDto centerDto = centerService.getById(wellnessLectureDto.getCenterId());
        final List<CenterContactNumberDto> centerContactNumberDtoList = centerContactNumberService.getAllByCenterId(wellnessLectureDto.getCenterId());
        final List<ReservationDto> reservationDtoList = reservationService.getAllByWellnessLectureId(wellnessLectureId);

        final ReservationDto myReservationDto = (memberId == null || reservationDtoList.isEmpty()) ? null
                : reservationDtoList.stream()
                .filter(reservationDto -> reservationDto.getMemberId().equals(memberId))
                .min((r1, r2) -> r2.getCreatedDate().compareTo(r1.getCreatedDate()))
                .orElse(null);

        final List<WellnessTicketIssuanceDto> myWellnessTicketIssuanceDtoList = memberId == null ? null : wellnessTicketIssuanceService.getAllByMemberIdAndStartDateBeforeAndExpireDateAfterAndIsDeleteFalse(memberId);
        final List<WellnessTicketManagementDto> wellnessTicketManagementDtoList = memberId == null ? null : wellnessTicketManagementService.getAllByIdList(wellnessLectureDto.getWellnessTicketManagementIdList());
        final List<WellnessLectureReviewDto> wellnessLectureReviewDtoList = wellnessLectureReviewService.getAllByWellnessClassId(wellnessLectureDto.getWellnessClassId());
        final List<Long> wellnessTicketIssuanceIdList = Optional.ofNullable(wellnessTicketManagementDtoList)
                .orElse(Collections.emptyList())
                .stream()
                .map(WellnessTicketManagementDto::getWellnessTicketIssuanceIdList)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .toList();

        final List<WellnessTicketIssuanceDto> myWellnessTicketIssuanceDtoAvailableList = Optional.ofNullable(myWellnessTicketIssuanceDtoList)
                .orElse(Collections.emptyList())
                .stream()
                .filter(dto -> wellnessTicketIssuanceIdList.contains(dto.getId())) // ID 기반 필터링
                .toList();

        final List<WellnessLectureMobileDto.ReservationExtension> reservationExtensionList = reservationDtoList.stream().map(reservationDto -> WellnessLectureMobileDto.ReservationExtension.builder().reservationDto(reservationDto).build()).toList();

        return WellnessLectureMobileDto.builder()
                .wellnessLectureDto(wellnessLectureDto)
                .teacherDto(teacherDto)
                .wellnessLectureTypeDto(wellnessLectureTypeDto)
                .myReservationDto(myReservationDto)
                .reservationExtensionList(reservationExtensionList)
                .centerDto(centerDto)
                .centerContactNumberDto(centerContactNumberDtoList)
                .myWellnessTicketIssuanceDtoAvaiableList(myWellnessTicketIssuanceDtoAvailableList)
                .wellnessLectureReviewDtoList(wellnessLectureReviewDtoList)
                .build();
    }

    @Override
    public List<WellnessLectureMobileDto> getWellnessLectureBookingDateListByWellnessClassId(Long wellnessClassId) {
        final List<WellnessLectureDto> wellnessLectureDtoList = wellnessLectureService.getAvailableBookingListByWellnessClassId(wellnessClassId);
        final List<Long> wellnessLectureIdList = wellnessLectureDtoList.stream().map(WellnessLectureDto::getId).toList();
        final List<ReservationDto> reservationDtoList = reservationService.getAllByWellnessLectureIdList(wellnessLectureIdList);

        return wellnessLectureDtoList.stream()
                .map(wellnessLectureDto -> {
                    final List<ReservationDto> targetReservationDtoList = reservationDtoList.stream()
                            .filter(reservationDto -> reservationDto.getWellnessLectureId().equals(wellnessLectureDto.getId()))
                            .toList();

                    final List<WellnessLectureMobileDto.ReservationExtension> reservationExtensionList = targetReservationDtoList.stream().map(reservationDto -> WellnessLectureMobileDto.ReservationExtension.builder().reservationDto(reservationDto).build()).toList();

                    return WellnessLectureMobileDto.builder()
                            .wellnessLectureDto(wellnessLectureDto)
                            .reservationExtensionList(reservationExtensionList)
                            .build();
                }).toList();
    }

    @Override
    public List<WellnessLectureMobileDto> getWellnessLectureManageListByDate(Long memberId, ZonedDateTime startDateTime, ZonedDateTime endDateTime) {
        final MemberDto memberDto = memberService.getById(memberId);
        return memberDto.getRoleList().stream().flatMap(roleDto -> {
                List<WellnessLectureDto> currentRoleWellnessLectures;
                if (Arrays.asList("OWNER", "MANAGER").contains(roleDto.getName())) {
                    currentRoleWellnessLectures = wellnessLectureService.getAllByCenterIdAndStartDateTimeBetweenAndIsDeleteFalse(
                            roleDto.getCenterId(), startDateTime, endDateTime
                    );
                }
                else if (roleDto.getName().equals("TEACHER")) {
                    final TeacherDto teacherDto = teacherService.getByCenterIdAndMemberId(roleDto.getCenterId(), memberId);
                    if (teacherDto == null) {
                        return Stream.empty();
                    }
                    currentRoleWellnessLectures = wellnessLectureService.getAllByCenterIdAndTeacherIdAndStartDateTimeBetweenAndIsDeleteFalse(
                            roleDto.getCenterId(), teacherDto.getId(), startDateTime, endDateTime
                    );
                }
                else {
                    return Stream.empty();
                }
                return currentRoleWellnessLectures.stream().map(wellnessLectureDto -> {
                    final List<ReservationDto> reservationDtoList = reservationService.getAllByWellnessLectureId(wellnessLectureDto.getId());
                    final TeacherDto teacherDto = teacherService.getById(wellnessLectureDto.getTeacherId());

                    final List<Long> wellnessTicketIssuanceIdList = reservationDtoList.stream().map(ReservationDto::getWellnessTicketIssuanceId).toList();
                    final List<WellnessTicketIssuanceDto> wellnessTicketIssuanceDtoList = wellnessTicketIssuanceService.getAllByIdList(wellnessTicketIssuanceIdList);

                    final List<WellnessLectureMobileDto.ReservationExtension> reservationExtensionList = reservationDtoList.stream()
                            .map(reservationDto -> {
                                final WellnessTicketIssuanceDto targetWellnessTicketIssuanceDto = wellnessTicketIssuanceDtoList
                                        .stream()
                                        .filter(wellnessTicketIssuanceDto -> wellnessTicketIssuanceDto.getId().equals(reservationDto.getWellnessTicketIssuanceId()))
                                        .findFirst()
                                        .orElse(null);

                                    return WellnessLectureMobileDto.ReservationExtension.builder()
                                    .reservationDto(reservationDto)
                                    .wellnessTicketIssuanceDto(targetWellnessTicketIssuanceDto)
                                    .build();
                            })
                            .toList();

                    return WellnessLectureMobileDto.builder()
                            .wellnessLectureDto(wellnessLectureDto)
                            .reservationExtensionList(reservationExtensionList)
                            .teacherDto(teacherDto)
                            .build();
                });
            })
            .collect(Collectors.toList());
    }
}
