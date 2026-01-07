package com.motiolab.nabusi_server.classPackage.wellnessClass.application;

import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.WellnessClassDto;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.WellnessLectureService;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.WellnessLectureDto;
import com.motiolab.nabusi_server.reservation.application.ReservationService;
import com.motiolab.nabusi_server.reservation.application.dto.ReservationDto;
import com.motiolab.nabusi_server.reservation.enums.ReservationStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WellnessClassAdminServiceDeletionTest {

    @Mock
    private WellnessClassService wellnessClassService;
    @Mock
    private WellnessLectureService wellnessLectureService;
    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private WellnessClassAdminServiceImpl wellnessClassAdminService;

    @Test
    @DisplayName("deleteWellnessClassById: 활성 예약이 있는 강의가 포함된 경우 예외를 발생시킨다.")
    void deleteWellnessClassById_WithActiveReservation() {
        // given
        Long classId = 1L;
        Long centerId = 10L;
        WellnessClassDto classDto = WellnessClassDto.builder().id(classId).centerId(centerId).build();

        WellnessLectureDto lectureDto = WellnessLectureDto.builder().id(100L).build();
        ReservationDto activeReservation = ReservationDto.builder().status(ReservationStatus.INAPP_RESERVATION).build();

        when(wellnessClassService.getByIdAndCenterId(classId, centerId)).thenReturn(classDto);
        when(wellnessLectureService.getAllByWellnessClassIdList(List.of(classId))).thenReturn(List.of(lectureDto));
        when(reservationService.getAllByWellnessLectureIdList(List.of(100L))).thenReturn(List.of(activeReservation));

        // when & then
        assertThrows(RuntimeException.class,
                () -> wellnessClassAdminService.deleteWellnessClassById(classId, centerId));
        verify(wellnessClassService, never()).update(any());
        verify(wellnessLectureService, never()).update(any());
    }

    @Test
    @DisplayName("deleteWellnessClassById: 예약이 없거나 취소된 예약만 있는 경우 정상적으로 클래스와 미래 강의를 삭제한다.")
    void deleteWellnessClassById_Success() {
        // given
        Long classId = 1L;
        Long centerId = 10L;
        WellnessClassDto classDto = WellnessClassDto.builder().id(classId).centerId(centerId).isDelete(false).build();

        ZonedDateTime now = ZonedDateTime.now();
        WellnessLectureDto pastLecture = WellnessLectureDto.builder().id(101L).startDateTime(now.minusDays(1))
                .isDelete(false).build();
        WellnessLectureDto futureLecture = WellnessLectureDto.builder().id(102L).startDateTime(now.plusDays(1))
                .isDelete(false).build();

        ReservationDto canceledReservation = ReservationDto.builder()
                .status(ReservationStatus.MEMBER_CANCELED_RESERVATION).build();

        when(wellnessClassService.getByIdAndCenterId(classId, centerId)).thenReturn(classDto);
        when(wellnessLectureService.getAllByWellnessClassIdList(List.of(classId)))
                .thenReturn(List.of(pastLecture, futureLecture));
        when(reservationService.getAllByWellnessLectureIdList(anyList())).thenReturn(List.of(canceledReservation));

        // when
        wellnessClassAdminService.deleteWellnessClassById(classId, centerId);

        // then
        verify(wellnessClassService).update(argThat(dto -> dto.getIsDelete().equals(true)));
        verify(wellnessLectureService, times(1))
                .update(argThat(dto -> dto.getId().equals(102L) && dto.getIsDelete().equals(true)));
        verify(wellnessLectureService, never()).update(argThat(dto -> dto.getId().equals(101L)));
    }
}
