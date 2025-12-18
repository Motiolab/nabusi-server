package com.motiolab.nabusi_server.reservation.ui;

import com.motiolab.nabusi_server.argumentResolver.MemberId;
import com.motiolab.nabusi_server.reservation.application.ReservationAdminService;
import com.motiolab.nabusi_server.reservation.application.dto.ReservationAdminDto;
import com.motiolab.nabusi_server.reservation.application.dto.request.CreateReservationAdminRequestV1;
import com.motiolab.nabusi_server.reservation.application.dto.response.GetReservationListByWellnessLectureIdAdminResponseV1;
import com.motiolab.nabusi_server.reservation.enums.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReservationAdminController {
    private final ReservationAdminService reservationAdminService;

    @PostMapping("/v1/admin/reservation/create/{centerId}")
    public ResponseEntity<Boolean> createReservation(@MemberId Long memberId, @PathVariable Long centerId, @RequestBody CreateReservationAdminRequestV1 createReservationAdminRequestV1) {
        createReservationAdminRequestV1.setActionMemberId(memberId);
        createReservationAdminRequestV1.setCenterId(centerId);
        createReservationAdminRequestV1.setStatus(ReservationStatus.ADMIN_RESERVATION);
        reservationAdminService.createReservation(createReservationAdminRequestV1);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/v1/admin/reservation/list/{centerId}")
    public ResponseEntity<List<GetReservationListByWellnessLectureIdAdminResponseV1>> getReservationListByWellnessLectureId(@PathVariable Long centerId, @RequestParam(defaultValue = "wellnessLectureId") Long wellnessLectureId) {
        final List<ReservationAdminDto> reservationAdminDtoList = reservationAdminService.getReservationListByWellnessLectureId(wellnessLectureId);
        final List<GetReservationListByWellnessLectureIdAdminResponseV1> reservationListByWellnessLectureIdAdminResponseV1List =  reservationAdminDtoList
                .stream()
                .map(reservationAdminDto -> GetReservationListByWellnessLectureIdAdminResponseV1.builder()
                        .reservationId(reservationAdminDto.getReservationDto().getId())
                        .memberId(reservationAdminDto.getMemberExtension().getMemberDto().getId())
                        .memberName(reservationAdminDto.getMemberExtension().getMemberDto().getName())
                        .memberMobile(reservationAdminDto.getMemberExtension().getMemberDto().getMobile())
                        .memberMemo(reservationAdminDto.getMemberExtension().getMemberMemoDto() != null ? reservationAdminDto.getMemberExtension().getMemberMemoDto().getContent() : "")
                        .wellnessTicketIssuanceName(reservationAdminDto.getWellnessTicketIssuanceDto() != null ? reservationAdminDto.getWellnessTicketIssuanceDto().getName(): "")
                        .wellnessTicketIssuanceBackgroundColor(reservationAdminDto.getWellnessTicketIssuanceDto() != null ? reservationAdminDto.getWellnessTicketIssuanceDto().getBackgroundColor(): "")
                        .wellnessTicketIssuanceType(reservationAdminDto.getWellnessTicketIssuanceDto() != null ? reservationAdminDto.getWellnessTicketIssuanceDto().getType(): "")
                        .wellnessTicketIssuanceTextColor(reservationAdminDto.getWellnessTicketIssuanceDto() != null ? reservationAdminDto.getWellnessTicketIssuanceDto().getTextColor(): "")
                        .reservationStatus(reservationAdminDto.getReservationDto().getStatus())
                        .reservationCreatedDate(reservationAdminDto.getReservationDto().getCreatedDate())
                        .build()
                ).toList();

        return ResponseEntity.ok(reservationListByWellnessLectureIdAdminResponseV1List);
    }
}
