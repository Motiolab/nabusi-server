package com.motiolab.nabusi_server.classPackage.wellnessLecture.ui;

import com.motiolab.nabusi_server.argumentResolver.MemberId;
import com.motiolab.nabusi_server.centerPackage.center.application.dto.CenterContactNumberDto;
import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.response.GetWellnessLectureBookingDateListByWellnessClassIdResponse;
import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.response.GetWellnessLectureManageByDateResponseV1;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.WellnessLectureMobileService;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.WellnessLectureMobileDto;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.response.GetAllWellnessLectureListByCenterIdAndDateResponse;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.response.GetAllWellnessLectureScheduleByCenterIdResponse;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.response.GetWellnessLectureDetailByWellnessLectureIdResponse;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.WellnessLectureReviewDto;
import com.motiolab.nabusi_server.reservation.enums.ReservationStatus;
import com.motiolab.nabusi_server.util.WellnessLectureReviewUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class WellnessLectureMobileController {
    private final WellnessLectureMobileService wellnessLectureMobileService;

    @GetMapping("/v1/mobile/wellness-lecture/{wellnessLectureId}")
    public ResponseEntity<GetWellnessLectureDetailByWellnessLectureIdResponse> getWellnessLectureDetailByWellnessLectureId(final @MemberId Long memberId, @PathVariable Long wellnessLectureId) {
        final WellnessLectureMobileDto wellnessLectureMobileDto = wellnessLectureMobileService.getWellnessLectureDetailByWellnessLectureId(memberId, wellnessLectureId);

        Boolean isReserved = wellnessLectureMobileDto.getMyReservationDto() != null &&
                (wellnessLectureMobileDto.getMyReservationDto().getStatus() == ReservationStatus.INAPP_RESERVATION ||
                        wellnessLectureMobileDto.getMyReservationDto().getStatus() == ReservationStatus.ADMIN_RESERVATION ||
                        wellnessLectureMobileDto.getMyReservationDto().getStatus() == ReservationStatus.ONSITE_RESERVATION);

        final GetWellnessLectureDetailByWellnessLectureIdResponse response = GetWellnessLectureDetailByWellnessLectureIdResponse.builder()
                .id(wellnessLectureMobileDto.getWellnessLectureDto().getId())
                .lectureImageUrlList(wellnessLectureMobileDto.getWellnessLectureDto().getLectureImageUrlList())
                .teacherId(wellnessLectureMobileDto.getTeacherDto().getId())
                .teacherName(wellnessLectureMobileDto.getTeacherDto().getName())
                .teacherNickName(wellnessLectureMobileDto.getTeacherDto().getUseNickName() ? wellnessLectureMobileDto.getTeacherDto().getNickName() : null)
                .teacherImageUrl(wellnessLectureMobileDto.getTeacherDto().getImageUrl())
                .teacherIntroduce(wellnessLectureMobileDto.getTeacherDto().getIntroduce())
                .rating(WellnessLectureReviewUtils.calculateAverageRating(wellnessLectureMobileDto.getWellnessLectureReviewDtoList()))
                .reviewCnt(wellnessLectureMobileDto.getWellnessLectureReviewDtoList() != null ? wellnessLectureMobileDto.getWellnessLectureReviewDtoList().size() : null)
                .name(wellnessLectureMobileDto.getWellnessLectureDto().getName())
                .isFullBooking(wellnessLectureMobileDto.getReservationExtensionList().size() >= wellnessLectureMobileDto.getWellnessLectureDto().getMaxReservationCnt())
                .wellnessLectureTypeName(wellnessLectureMobileDto.getWellnessLectureTypeDto().name())
                .wellnessLectureTypeDescription(wellnessLectureMobileDto.getWellnessLectureTypeDto().description())
                .description(wellnessLectureMobileDto.getWellnessLectureDto().getDescription())
                .startDateTime(wellnessLectureMobileDto.getWellnessLectureDto().getStartDateTime())
                .endDateTime(wellnessLectureMobileDto.getWellnessLectureDto().getEndDateTime())
                .centerId(wellnessLectureMobileDto.getCenterDto().getId())
                .centerName(wellnessLectureMobileDto.getCenterDto().getName())
                .centerAddress(wellnessLectureMobileDto.getCenterDto().getAddress())
                .centerDetailAddress(wellnessLectureMobileDto.getCenterDto().getDetailAddress())
                .room(wellnessLectureMobileDto.getWellnessLectureDto().getRoom())
                .price(wellnessLectureMobileDto.getWellnessLectureDto().getPrice())
                .isReserved(isReserved)
                .reservationStatus(wellnessLectureMobileDto.getMyReservationDto() == null ? null : wellnessLectureMobileDto.getMyReservationDto().getStatus())
                .reservationId(wellnessLectureMobileDto.getMyReservationDto() != null ? wellnessLectureMobileDto.getMyReservationDto().getId() : null)
                .reservedWellnessTicketIssuanceId(wellnessLectureMobileDto.getMyReservationDto() != null ? wellnessLectureMobileDto.getMyReservationDto().getWellnessTicketIssuanceId() : null)
                .centerContactNumber(wellnessLectureMobileDto.getCenterContactNumberDto().stream().map(CenterContactNumberDto::getNumber).toList())
                .wellnessTicketIssuanceAvailableList(wellnessLectureMobileDto.getMyWellnessTicketIssuanceDtoAvaiableList()
                        .stream().map(wellnessTicketIssuanceDto -> GetWellnessLectureDetailByWellnessLectureIdResponse.WellnessTicketIssuanceAvailable.builder()
                                .id(wellnessTicketIssuanceDto.getId())
                                .name(wellnessTicketIssuanceDto.getName())
                                .startDate(wellnessTicketIssuanceDto.getStartDate())
                                .expireDate(wellnessTicketIssuanceDto.getExpireDate())
                                .remainingCnt(wellnessTicketIssuanceDto.getRemainingCnt())
                                .remainingDate(ZonedDateTime.now().until(wellnessTicketIssuanceDto.getExpireDate(), ChronoUnit.DAYS))
                                .build()

                        )
                        .toList())
                .build();

        return ResponseEntity.ok(response);

    }

    private Double calculateAverageRating(List<WellnessLectureReviewDto> reviewList) {
        if (reviewList == null || reviewList.isEmpty()) {
            return null;
        }

        double sum = reviewList.stream()
                .filter(review -> review.getRating() != null)
                .mapToInt(WellnessLectureReviewDto::getRating)
                .average()
                .orElse(0.0);

        return Math.round(sum * 10.0) / 10.0;
    }

    @GetMapping("/v1/mobile/wellness-lecture/schedule")
    public ResponseEntity<GetAllWellnessLectureScheduleByCenterIdResponse> getAllWellnessLectureScheduleByCenterId(final @MemberId Long memberId, final @RequestParam Long centerId) {
        final List<WellnessLectureMobileDto> wellnessLectureMobileDtoList = wellnessLectureMobileService.getAllWellnessLectureListByCenterId(memberId, centerId);
        final List<WellnessLectureMobileDto> sortedList = wellnessLectureMobileDtoList.stream()
                .sorted(Comparator.comparing((WellnessLectureMobileDto dto) -> dto.getWellnessLectureDto().getStartDateTime(), Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();

        if (sortedList.isEmpty()) {
            return ResponseEntity.ok(GetAllWellnessLectureScheduleByCenterIdResponse.builder().build());
        }

        final List<ZonedDateTime> eventDays = wellnessLectureMobileDtoList.stream()
                .filter(wellnessLectureMobileDto -> {
                    if (wellnessLectureMobileDto.getReservationExtensionList() != null) {
                        return wellnessLectureMobileDto.getReservationExtensionList().stream()
                                .anyMatch(reservationExtension -> reservationExtension.getReservationDto().getMemberId().equals(memberId));
                    }
                    return false;
                })
                .map(wellnessLectureMobileDto -> wellnessLectureMobileDto.getWellnessLectureDto().getStartDateTime())
                .toList();

        final List<ZonedDateTime> activeDays = wellnessLectureMobileDtoList.stream()
                .map(wellnessLectureMobileDto -> wellnessLectureMobileDto.getWellnessLectureDto().getStartDateTime())
                .toList();

        final GetAllWellnessLectureScheduleByCenterIdResponse response = GetAllWellnessLectureScheduleByCenterIdResponse.builder()
                .initSelectedDate(wellnessLectureMobileDtoList.get(0).getWellnessLectureDto().getStartDateTime())
                .holidays(getKoreanHolidays())
                .eventDays(eventDays)
                .activeDays(activeDays)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/v1/mobile/wellness-lecture/list")
    public ResponseEntity<List<GetAllWellnessLectureListByCenterIdAndDateResponse>> getAllWellnessLectureListByCenterIdAndDate(final @MemberId Long memberId, final @RequestParam Long centerId, final @RequestParam ZonedDateTime startDateTime, final @RequestParam ZonedDateTime endDateTime) {
        List<WellnessLectureMobileDto> wellnessLectureMobileDtoList = wellnessLectureMobileService.getAllWellnessLectureListByCenterIdAndDate(memberId, centerId, startDateTime, endDateTime);

        final List<GetAllWellnessLectureListByCenterIdAndDateResponse> responseList = wellnessLectureMobileDtoList
                .stream()
                .map(wellnessLectureMobileDto -> {
                    String status;
                    if (wellnessLectureMobileDto.getMyReservationDto() != null) {
                        if(wellnessLectureMobileDto.getMyReservationDto().getStatus().equals(ReservationStatus.ABSENT)) {
                            status = "결석";
                        }else if(wellnessLectureMobileDto.getMyReservationDto().getStatus().equals(ReservationStatus.CHECK_IN)) {
                            status = "출석";
                        }else if(wellnessLectureMobileDto.getMyReservationDto().getStatus().equals(ReservationStatus.MEMBER_CANCELED_RESERVATION)) {
                            status = "예약취소";
                        }else if(wellnessLectureMobileDto.getMyReservationDto().getStatus().equals(ReservationStatus.ADMIN_CANCELED_RESERVATION)) {
                            status = "예약취소";
                        }
                        else {
                            status = "예약완료";
                        }
                    }else {
                        ZonedDateTime lectureStartDateTime = wellnessLectureMobileDto.getWellnessLectureDto().getStartDateTime();
                        ZonedDateTime lectureEndDateTime = wellnessLectureMobileDto.getWellnessLectureDto().getEndDateTime();
                        if(lectureStartDateTime.isBefore(ZonedDateTime.now()) && lectureEndDateTime.isAfter(ZonedDateTime.now())) {
                            status = "수업중";
                        } else if(lectureEndDateTime.isBefore(ZonedDateTime.now())) {
                            status = "수업완료";
                        } else if(wellnessLectureMobileDto.getReservationExtensionList().size() >= wellnessLectureMobileDto.getWellnessLectureDto().getMaxReservationCnt()) {
                            status = "정원초과";
                        } else {
                            status = "예약가능";
                        }
                    }

                    return GetAllWellnessLectureListByCenterIdAndDateResponse.builder()
                            .id(wellnessLectureMobileDto.getWellnessLectureDto().getId())
                            .lectureImageUrl(wellnessLectureMobileDto.getWellnessLectureDto().getLectureImageUrlList().get(0))
                            .status(status)
                            .name(wellnessLectureMobileDto.getWellnessLectureDto().getName())
                            .teacherName(wellnessLectureMobileDto.getTeacherDto().getName())
                            .room(wellnessLectureMobileDto.getWellnessLectureDto().getRoom())
                            .startDateTime(wellnessLectureMobileDto.getWellnessLectureDto().getStartDateTime())
                            .endDateTime(wellnessLectureMobileDto.getWellnessLectureDto().getEndDateTime())
                            .isFullBooking(wellnessLectureMobileDto.getReservationExtensionList().size() >= wellnessLectureMobileDto.getWellnessLectureDto().getMaxReservationCnt())
                            .wellnessLectureTypeName(wellnessLectureMobileDto.getWellnessLectureTypeDto().name())
                            .wellnessLectureTypeDescription(wellnessLectureMobileDto.getWellnessLectureTypeDto().description())
                            .isCreatedReview(wellnessLectureMobileDto.getMyWellnessLectureReviewDto() != null)
                            .build();
                }).toList();

        return ResponseEntity.ok(responseList);
    }


    @GetMapping("/v1/mobile/wellness-lecture/booking/date/{wellnessClassId}")
    public ResponseEntity<GetWellnessLectureBookingDateListByWellnessClassIdResponse> getWellnessLectureBookingDateListByWellnessClassId(final @MemberId Long memberId, @PathVariable Long wellnessClassId) {
        final List<WellnessLectureMobileDto> wellnessLectureMobileDtoList = wellnessLectureMobileService.getWellnessLectureBookingDateListByWellnessClassId(wellnessClassId);
        final List<WellnessLectureMobileDto> sortedList = wellnessLectureMobileDtoList.stream()
                .sorted(Comparator.comparing((WellnessLectureMobileDto dto) -> dto.getWellnessLectureDto().getStartDateTime(), Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();

        if (sortedList.isEmpty()) {
            return ResponseEntity.ok(GetWellnessLectureBookingDateListByWellnessClassIdResponse.builder().build());
        }

        final List<ZonedDateTime> eventDays = wellnessLectureMobileDtoList.stream()
                .filter(wellnessLectureMobileDto -> {
                    if (wellnessLectureMobileDto.getReservationExtensionList() != null) {
                        return wellnessLectureMobileDto.getReservationExtensionList().stream()
                                .anyMatch(reservationExtension -> reservationExtension.getReservationDto().getMemberId().equals(memberId));
                    }
                    return false;
                })
                .map(wellnessLectureMobileDto -> wellnessLectureMobileDto.getWellnessLectureDto().getStartDateTime())
                .toList();

        final List<ZonedDateTime> activeDays = wellnessLectureMobileDtoList.stream()
                .map(wellnessLectureMobileDto -> wellnessLectureMobileDto.getWellnessLectureDto().getStartDateTime())
                .toList();

        final List<GetWellnessLectureBookingDateListByWellnessClassIdResponse.WellnessLectureIdAndDate> wellnessLectureIdAndDateList = wellnessLectureMobileDtoList.stream()
                .map(wellnessLectureMobileDto -> GetWellnessLectureBookingDateListByWellnessClassIdResponse.WellnessLectureIdAndDate.builder()
                        .id(wellnessLectureMobileDto.getWellnessLectureDto().getId())
                        .startDateTime(wellnessLectureMobileDto.getWellnessLectureDto().getStartDateTime())
                        .isBooked(wellnessLectureMobileDto.getReservationExtensionList().stream().anyMatch(reservationExtension -> reservationExtension.getReservationDto().getMemberId().equals(memberId)))
                        .isFullBooking(wellnessLectureMobileDto.getReservationExtensionList().size() >= wellnessLectureMobileDto.getWellnessLectureDto().getMaxReservationCnt())
                        .build())
                .toList();

        final GetWellnessLectureBookingDateListByWellnessClassIdResponse response = GetWellnessLectureBookingDateListByWellnessClassIdResponse.builder()
                .initSelectedDate(wellnessLectureMobileDtoList.get(0).getWellnessLectureDto().getStartDateTime())
                .holidays(getKoreanHolidays())
                .eventDays(eventDays)
                .activeDays(activeDays)
                .wellnessLectureIdAndDateList(wellnessLectureIdAndDateList)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/v1/mobile/wellness-lecture/manage/date")
    public ResponseEntity<List<GetWellnessLectureManageByDateResponseV1>> getWellnessLectureManageListByDate(final @MemberId Long memberId, final @RequestParam ZonedDateTime startDateTime, final @RequestParam ZonedDateTime endDateTime) {
        final List<WellnessLectureMobileDto> wellnessLectureMobileDtoList = wellnessLectureMobileService.getWellnessLectureManageListByDate(memberId, startDateTime, endDateTime);
        final List<GetWellnessLectureManageByDateResponseV1> wellnessLectureManageByDateResponseV1List = wellnessLectureMobileDtoList.stream().map(wellnessLectureMobileDto -> {
            final List<GetWellnessLectureManageByDateResponseV1.Reservation> reservationList = wellnessLectureMobileDto.getReservationExtensionList()
                    .stream()
                    .map(reservationExtension -> GetWellnessLectureManageByDateResponseV1.Reservation.builder()
                            .id(reservationExtension.getReservationDto().getId())
                            .centerId(reservationExtension.getReservationDto().getCenterId())
                            .memberId(reservationExtension.getReservationDto().getMemberId())
                            .status(reservationExtension.getReservationDto().getStatus())
                            .wellnessTicketIssuanceId(reservationExtension.getReservationDto().getWellnessTicketIssuanceId())
                            .lastUpdatedDate(reservationExtension.getReservationDto().getLastUpdatedDate())
                            .createdDate(reservationExtension.getReservationDto().getCreatedDate())
                            .wellnessIssuanceName(reservationExtension.getWellnessTicketIssuanceDto().getName())
                            .expireDate(reservationExtension.getWellnessTicketIssuanceDto().getExpireDate())
                            .type(reservationExtension.getWellnessTicketIssuanceDto().getType())
                            .remainingCnt(reservationExtension.getWellnessTicketIssuanceDto().getRemainingCnt())
                            .totalUsableCnt(reservationExtension.getWellnessTicketIssuanceDto().getTotalUsableCnt())
                            .build())
                    .toList();

            return GetWellnessLectureManageByDateResponseV1.builder()
                    .id(wellnessLectureMobileDto.getWellnessLectureDto().getId())
                    .name(wellnessLectureMobileDto.getWellnessLectureDto().getName())
                    .centerId(wellnessLectureMobileDto.getWellnessLectureDto().getCenterId())
                    .maxReservationCnt(wellnessLectureMobileDto.getWellnessLectureDto().getMaxReservationCnt())
                    .room(wellnessLectureMobileDto.getWellnessLectureDto().getRoom())
                    .lectureImageUrl(wellnessLectureMobileDto.getWellnessLectureDto().getLectureImageUrlList().get(0))
                    .startDateTime(wellnessLectureMobileDto.getWellnessLectureDto().getStartDateTime())
                    .endDateTime(wellnessLectureMobileDto.getWellnessLectureDto().getEndDateTime())
                    .isDelete(wellnessLectureMobileDto.getWellnessLectureDto().getIsDelete())
                    .teacherName(wellnessLectureMobileDto.getTeacherDto().getName())
                    .reservationList(reservationList)
                    .build();
        }).toList();

        return ResponseEntity.ok(wellnessLectureManageByDateResponseV1List);
    }


    public List<String> getKoreanHolidays() {
        return Arrays.asList(
                "2025-01-01", "2025-01-28", "2025-01-29", "2025-01-30", "2025-03-01", "2025-03-03",
                "2025-05-05", "2025-05-06", "2025-06-06", "2025-08-15", "2025-10-03", "2025-10-05",
                "2025-10-06", "2025-10-07", "2025-10-08", "2025-10-09", "2025-12-25",

                "2026-01-01", "2026-02-16", "2026-02-17", "2026-02-18", "2026-03-01", "2026-05-05",
                "2026-05-24", "2026-06-06", "2026-08-15", "2026-09-25", "2026-09-26", "2026-09-27",
                "2026-09-28", "2026-10-03", "2026-10-09", "2026-12-25",

                "2027-01-01", "2027-02-06", "2027-02-07", "2027-02-08", "2027-03-01", "2027-05-03",
                "2027-05-05", "2027-06-06", "2027-08-15", "2027-09-15", "2027-09-16", "2027-09-17",
                "2027-09-18", "2027-10-03", "2027-10-09", "2027-12-25"
        );
    }
}
