package com.motiolab.nabusi_server.classPackage.wellnessLecture.application;

import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.WellnessLectureDto;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.request.UpdateWellnessLectureListAdminRequestV1;
import com.motiolab.nabusi_server.reservation.application.ReservationService;
import com.motiolab.nabusi_server.reservation.application.dto.ReservationDto;
import com.motiolab.nabusi_server.reservation.enums.ReservationStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WellnessLectureAdminServiceTest {

    @Mock
    private WellnessLectureService wellnessLectureService;

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private WellnessLectureAdminServiceImpl wellnessLectureAdminService;

    @Test
    @DisplayName("updateWellnessLectureList: idList에 포함된 모든 수업을 수정하되, 요청에 없는 필드(시간 등)는 유지한다.")
    void updateWellnessLectureList() {
        // given
        List<Long> idList = List.of(1L, 2L);
        UpdateWellnessLectureListAdminRequestV1 request = UpdateWellnessLectureListAdminRequestV1.builder()
                .idList(idList)
                .name("수정된 수업 이름")
                .build();

        java.time.ZonedDateTime existingTime = java.time.ZonedDateTime.now();
        WellnessLectureDto existing1 = WellnessLectureDto.builder().id(1L).name("수업1").startDateTime(existingTime)
                .build();
        WellnessLectureDto existing2 = WellnessLectureDto.builder().id(2L).name("수업2").startDateTime(existingTime)
                .build();

        when(wellnessLectureService.getAllByIdList(idList)).thenReturn(List.of(existing1, existing2));

        // when
        wellnessLectureAdminService.updateWellnessLectureList(request);

        // then
        verify(wellnessLectureService, times(2)).update(
                argThat(dto -> dto.getName().equals("수정된 수업 이름") && dto.getStartDateTime().equals(existingTime)));
    }

    @Test
    @DisplayName("deleteWellnessLectureListById: 활성 예약이 있는 경우 예외를 발생시킨다.")
    void deleteWellnessLectureListById_WithActiveReservation() {
        // given
        List<Long> idList = List.of(1L, 2L);
        ReservationDto activeReservation = ReservationDto.builder().status(ReservationStatus.INAPP_RESERVATION).build();
        when(reservationService.getAllByWellnessLectureIdList(idList)).thenReturn(List.of(activeReservation));

        // when & then
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,
                () -> wellnessLectureAdminService.deleteWellnessLectureListById(idList));
        verify(wellnessLectureService, never()).delete(anyLong());
    }

    @Test
    @DisplayName("deleteWellnessLectureListById: 취소된 예약만 있거나 예약이 없는 경우 정상적으로 삭제한다.")
    void deleteWellnessLectureListById_Success() {
        // given
        List<Long> idList = List.of(1L, 2L);
        ReservationDto canceledReservation = ReservationDto.builder()
                .status(ReservationStatus.MEMBER_CANCELED_RESERVATION).build();
        when(reservationService.getAllByWellnessLectureIdList(idList)).thenReturn(List.of(canceledReservation));

        // when
        wellnessLectureAdminService.deleteWellnessLectureListById(idList);

        // then
        verify(wellnessLectureService, times(1)).delete(1L);
        verify(wellnessLectureService, times(1)).delete(2L);
    }

    @Test
    @DisplayName("deleteWellnessLectureById: 활성 예약이 있는 경우 예외를 발생시킨다.")
    void deleteWellnessLectureById_WithActiveReservation() {
        // given
        Long id = 1L;
        ReservationDto activeReservation = ReservationDto.builder().status(ReservationStatus.INAPP_RESERVATION).build();
        when(reservationService.getAllByWellnessLectureId(id)).thenReturn(List.of(activeReservation));

        // when & then
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,
                () -> wellnessLectureAdminService.deleteWellnessLectureById(id, false));
        verify(wellnessLectureService, never()).delete(anyLong());
    }

    @Test
    @DisplayName("deleteWellnessLectureById: 예약이 없는 경우 정상적으로 삭제한다.")
    void deleteWellnessLectureById_Success() {
        // given
        Long id = 1L;
        when(reservationService.getAllByWellnessLectureId(id)).thenReturn(List.of());

        // when
        wellnessLectureAdminService.deleteWellnessLectureById(id, false);

        // then
        verify(wellnessLectureService, times(1)).delete(id);
    }
}
