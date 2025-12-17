package com.positivehotel.nabusi_server.reservation.application;

import com.positivehotel.nabusi_server.classPackage.wellnessLecture.application.WellnessLectureService;
import com.positivehotel.nabusi_server.classPackage.wellnessLecture.application.dto.WellnessLectureDto;
import com.positivehotel.nabusi_server.exception.customException.*;
import com.positivehotel.nabusi_server.memberPackage.member.application.MemberService;
import com.positivehotel.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.positivehotel.nabusi_server.memberPackage.memberMemo.application.MemberMemoService;
import com.positivehotel.nabusi_server.memberPackage.memberMemo.application.dto.MemberMemoDto;
import com.positivehotel.nabusi_server.reservation.application.dto.ReservationAdminDto;
import com.positivehotel.nabusi_server.reservation.application.dto.ReservationDto;
import com.positivehotel.nabusi_server.reservation.application.dto.request.CreateReservationAdminRequestV1;
import com.positivehotel.nabusi_server.reservation.enums.ReservationStatus;
import com.positivehotel.nabusi_server.ticketPackage.enums.CntChangedReason;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.application.WellnessTicketIssuanceService;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.WellnessTicketIssuanceDto;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuanceHistory.application.WellnessTicketIssuanceHistoryService;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuanceHistory.application.dto.WellnessTicketIssuanceHistoryDto;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketManagement.application.WellnessTicketManagementService;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketManagement.application.dto.WellnessTicketManagementDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Log
public class ReservationAdminServiceImpl implements ReservationAdminService{
    private final ReservationService reservationService;
    private final MemberService memberService;
    private final MemberMemoService memberMemoService;
    private final WellnessTicketIssuanceService wellnessTicketIssuanceService;
    private final WellnessLectureService wellnessLectureService;
    private final WellnessTicketIssuanceHistoryService wellnessTicketIssuanceHistoryService;
    private final WellnessTicketManagementService wellnessTicketManagementService;


    @Transactional
    @Override
    public void createReservation(CreateReservationAdminRequestV1 createReservationAdminRequestV1) {
        // todo 예약 정책에서 예야 가능한 시간인지 체크하는 로직 추가
        final WellnessLectureDto wellnessLectureDto = wellnessLectureService.getById(createReservationAdminRequestV1.getWellnessLectureId());
        if (wellnessLectureDto == null) throw new NotFoundException(WellnessLectureDto.class, createReservationAdminRequestV1.getWellnessLectureId());

        checkWellnessLectureToReservation(wellnessLectureDto);
        WellnessTicketIssuanceDto wellnessTicketIssuanceDto = null;
        if (createReservationAdminRequestV1.getWellnessTicketIssuanceId() != null) {
            wellnessTicketIssuanceDto = wellnessTicketIssuanceService.getById(createReservationAdminRequestV1.getWellnessTicketIssuanceId());
            if (wellnessTicketIssuanceDto == null) {
                throw new NotFoundException(WellnessTicketIssuanceDto.class, createReservationAdminRequestV1.getWellnessTicketIssuanceId());
            }
            checkWellnessTicketIssuanceToReservation(wellnessTicketIssuanceDto);
            checkLimitWellnessTicketIssuanceToReservation(wellnessTicketIssuanceDto, wellnessLectureDto.getStartDateTime());

            final WellnessTicketManagementDto wellnessTicketManagementDto = wellnessTicketManagementService.getByWellnessTicketIssuanceId(wellnessTicketIssuanceDto.getId());
            if (wellnessTicketManagementDto == null) {
                throw new NotFoundException(WellnessTicketManagementDto.class, wellnessTicketIssuanceDto.getId());
            }
            if(!wellnessLectureDto.getWellnessTicketManagementIdList().contains(wellnessTicketManagementDto.getId())){
                throw new TicketNotLinkedException(WellnessTicketManagementDto.class, wellnessTicketIssuanceDto.getId());
            }
        }

        final ReservationDto reservationDto = ReservationDto.builder()
                .centerId(createReservationAdminRequestV1.getCenterId())
                .memberId(createReservationAdminRequestV1.getMemberId())
                .actionMemberId(createReservationAdminRequestV1.getActionMemberId())
                .status(createReservationAdminRequestV1.getStatus())
                .wellnessLectureId(createReservationAdminRequestV1.getWellnessLectureId())
                .wellnessTicketIssuanceId(createReservationAdminRequestV1.getWellnessTicketIssuanceId())
                .build();

        final ReservationDto savedReservationDto = reservationService.create(reservationDto);

        final WellnessTicketIssuanceHistoryDto wellnessTicketIssuanceHistoryDto = WellnessTicketIssuanceHistoryDto.builder()
                .changedCnt(-1)
                .wellnessLectureId(createReservationAdminRequestV1.getWellnessLectureId())
                .reservationId(savedReservationDto.getId())
                .actionMemberId(createReservationAdminRequestV1.getActionMemberId())
                .wellnessTicketIssuanceId(createReservationAdminRequestV1.getWellnessTicketIssuanceId())
                .reason(CntChangedReason.ADMIN_RESERVATION)
                .build();
        wellnessTicketIssuanceHistoryService.create(wellnessTicketIssuanceHistoryDto);

        if (wellnessTicketIssuanceDto != null) {
            wellnessTicketIssuanceDto.setRemainingCnt(wellnessTicketIssuanceDto.getRemainingCnt() - 1);
            if ((wellnessTicketIssuanceDto.getType().equals("COUNT") && (wellnessTicketIssuanceDto.getRemainingCnt() <= 0)|| wellnessTicketIssuanceDto.getExpireDate().isBefore(ZonedDateTime.now()))) {
                wellnessTicketIssuanceDto.setIsDelete(true);
            }
            wellnessTicketIssuanceService.update(wellnessTicketIssuanceDto);
        }
    }

    private void checkLimitWellnessTicketIssuanceToReservation(WellnessTicketIssuanceDto wellnessTicketIssuanceDto, ZonedDateTime lectureStartTime){
        if (wellnessTicketIssuanceDto.getLimitType().equals("NONE")) {
            return;
        }

        final List<ReservationDto> reservationDtoList = reservationService.getAllByWellnessTicketIssuanceId(wellnessTicketIssuanceDto.getId())
                .stream()
                .filter(reservationDto -> !(reservationDto.getStatus().equals(ReservationStatus.MEMBER_CANCELED_RESERVATION) || reservationDto.getStatus().equals(ReservationStatus.ADMIN_CANCELED_RESERVATION)))
                .toList();

        final List<Long> wellnessLectureIdList = reservationDtoList.stream().map(ReservationDto::getWellnessLectureId).toList();
        final List<WellnessLectureDto> wellnessLectureDtoList = wellnessLectureService.getAllByIdList(wellnessLectureIdList);

        List<ReservationDto> filteredReservations = switch (wellnessTicketIssuanceDto.getLimitType()) {
            case "WEEK" -> reservationDtoList.stream()
                    .filter(reservation -> {
                        final WellnessLectureDto targetWellnessLectureDto = wellnessLectureDtoList.stream().filter(wellnessLectureDto -> wellnessLectureDto.getId().equals(reservation.getWellnessLectureId())).findFirst().orElseThrow(() -> new RuntimeException("Error WellnessLecture is Not Available. id:" + reservation.getWellnessLectureId()));
                        return isSameWeek(targetWellnessLectureDto.getStartDateTime(), lectureStartTime);
                    }).toList();
            case "MONTH" -> reservationDtoList.stream()
                    .filter(reservation -> {
                        final WellnessLectureDto targetWellnessLectureDto = wellnessLectureDtoList.stream().filter(wellnessLectureDto -> wellnessLectureDto.getId().equals(reservation.getWellnessLectureId())).findFirst().orElseThrow(() -> new RuntimeException("Error WellnessLecture is Not Available. id:" + reservation.getWellnessLectureId()));
                        return isSameMonth(targetWellnessLectureDto.getStartDateTime(), lectureStartTime);
                    }).toList();
            default -> List.of();
        };

        if (filteredReservations.size() >= wellnessTicketIssuanceDto.getLimitCnt()) {
            throw new TicketInvalidException(WellnessTicketIssuanceDto.class, wellnessTicketIssuanceDto.getId());
        }
    }

    public Boolean isSameMonth(ZonedDateTime startDateTime, ZonedDateTime targetDate) {
        return startDateTime.getYear() == targetDate.getYear() &&
                startDateTime.getMonthValue() == targetDate.getMonthValue();
    }

    public Boolean isSameWeek(ZonedDateTime startDateTime, ZonedDateTime targetDate) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        int startWeek = startDateTime.get(weekFields.weekOfWeekBasedYear());
        int currentWeek = targetDate.get(weekFields.weekOfWeekBasedYear());

        int startYear = startDateTime.getYear();
        int currentYear = targetDate.getYear();

        return startYear == currentYear && startWeek == currentWeek;
    }

    private void checkWellnessLectureToReservation(WellnessLectureDto wellnessLectureDto) {
        if(wellnessLectureDto.getIsDelete()) {
            throw new DeletedAlreadyException(WellnessTicketIssuanceDto.class, wellnessLectureDto.getId());
        }

        final List<ReservationDto> reservationDtoList = reservationService.getAllByWellnessLectureId(wellnessLectureDto.getId());
        final int reservationCntWithoutCancel = reservationDtoList.stream().filter(reservationDto -> !reservationDto.getStatus().equals(ReservationStatus.ADMIN_RESERVATION)).toList().size();

        if(reservationCntWithoutCancel >= wellnessLectureDto.getMaxReservationCnt()){
            throw new FullReservationException(WellnessLectureDto.class, wellnessLectureDto.getId());
        }
    }

    private void checkWellnessTicketIssuanceToReservation(WellnessTicketIssuanceDto wellnessTicketIssuanceDto) {
        if(wellnessTicketIssuanceDto.getIsDelete()) {
            throw new DeletedAlreadyException(WellnessTicketIssuanceDto.class, wellnessTicketIssuanceDto.getId());
        }

        final ZonedDateTime now = ZonedDateTime.now();
        if (wellnessTicketIssuanceDto.getType().equals("COUNT") && (wellnessTicketIssuanceDto.getRemainingCnt() <= 0 || wellnessTicketIssuanceDto.getExpireDate().isBefore(now))) {
            wellnessTicketIssuanceDto.setIsDelete(true);
            wellnessTicketIssuanceService.update(wellnessTicketIssuanceDto);
            throw new TicketInvalidException(WellnessTicketIssuanceDto.class, wellnessTicketIssuanceDto.getId());
        }
        if(wellnessTicketIssuanceDto.getType().equals("PERIOD") && wellnessTicketIssuanceDto.getExpireDate().isBefore(now)){wellnessTicketIssuanceDto.setIsDelete(true);
            wellnessTicketIssuanceService.update(wellnessTicketIssuanceDto);
            throw new TicketInvalidException(WellnessTicketIssuanceDto.class, wellnessTicketIssuanceDto.getId());
        }
    }

    @Override
    public List<ReservationAdminDto> getReservationListByWellnessLectureId(Long wellnessLectureId) {
        final List<ReservationDto> reservationDtoList = reservationService.getAllByWellnessLectureId(wellnessLectureId);
        final List<Long> memberIdList = reservationDtoList.stream().map(ReservationDto::getMemberId).distinct().toList();
        final List<MemberDto> memberDtoList = memberService.getAllByIdList(memberIdList);
        final List<MemberMemoDto> memberMemoDtoList = memberMemoService.getAllByMemberIdList(memberIdList);
        final List<Long> wellnessTicketIssuanceIdList = reservationDtoList.stream().map(ReservationDto::getWellnessTicketIssuanceId).distinct().toList();
        final List<WellnessTicketIssuanceDto> wellnessTicketIssuanceDtoList = wellnessTicketIssuanceService.getAllByIdList(wellnessTicketIssuanceIdList);

        return reservationDtoList
                .stream()
                .map(reservationDto -> {
                    final ReservationAdminDto.MemberExtension memberExtension = ReservationAdminDto.MemberExtension.builder()
                            .memberDto(memberDtoList
                                    .stream()
                                    .filter(memberDto -> memberDto.getId().equals(reservationDto.getMemberId()))
                                    .findFirst()
                                    .orElseThrow(() -> new NotFoundException(MemberDto.class, reservationDto.getMemberId())))
                            .memberMemoDto(memberMemoDtoList
                                    .stream()
                                    .filter(memberMemoDto -> memberMemoDto.getMemberId().equals(reservationDto.getMemberId()))
                                    .findFirst()
                                    .orElse(null))
                            .build();

                    final WellnessTicketIssuanceDto targetWellnessTicketIssuanceDto = wellnessTicketIssuanceDtoList
                            .stream()
                            .filter(wellnessTicketIssuanceDto -> wellnessTicketIssuanceDto.getId().equals(reservationDto.getWellnessTicketIssuanceId()))
                            .findFirst()
                            .orElse(null);

                    return ReservationAdminDto.builder()
                            .reservationDto(reservationDto)
                            .memberExtension(memberExtension)
                            .wellnessTicketIssuanceDto(targetWellnessTicketIssuanceDto)
                            .build();
                })
                .toList();
    }
}
