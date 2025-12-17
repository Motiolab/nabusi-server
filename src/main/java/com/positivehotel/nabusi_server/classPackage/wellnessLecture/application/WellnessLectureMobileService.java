package com.positivehotel.nabusi_server.classPackage.wellnessLecture.application;

import com.positivehotel.nabusi_server.classPackage.wellnessLecture.application.dto.WellnessLectureMobileDto;

import java.time.ZonedDateTime;
import java.util.List;

public interface WellnessLectureMobileService {
    List<WellnessLectureMobileDto> getAllWellnessLectureListByCenterIdAndDate(Long memberId, Long centerId, ZonedDateTime startDateTime, ZonedDateTime endDateTime);
    List<WellnessLectureMobileDto> getAllWellnessLectureListByCenterId(Long memberId, Long centerId);
    WellnessLectureMobileDto getWellnessLectureDetailByWellnessLectureId(Long memberId, Long wellnessLectureId);
    List<WellnessLectureMobileDto> getWellnessLectureBookingDateListByWellnessClassId(Long wellnessClassId);
    List<WellnessLectureMobileDto> getWellnessLectureManageListByDate(Long memberId, ZonedDateTime startDateTime, ZonedDateTime endDateTime);
}
