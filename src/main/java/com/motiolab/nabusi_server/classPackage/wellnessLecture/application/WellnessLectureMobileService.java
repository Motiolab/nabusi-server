package com.motiolab.nabusi_server.classPackage.wellnessLecture.application;

import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.WellnessLectureMobileDto;

import java.time.ZonedDateTime;
import java.util.List;

public interface WellnessLectureMobileService {
    List<WellnessLectureMobileDto> getAllWellnessLectureListByCenterIdAndDate(Long memberId, Long centerId, ZonedDateTime startDateTime, ZonedDateTime endDateTime);
    List<WellnessLectureMobileDto> getAllWellnessLectureListByMemberIdAndDate(Long memberId, ZonedDateTime startDateTime, ZonedDateTime endDateTime);
    List<WellnessLectureMobileDto> getAllWellnessLectureListByCenterId(Long memberId, Long centerId);
    List<WellnessLectureMobileDto> getAllWellnessLectureListByMemberId(Long memberId);
    WellnessLectureMobileDto getWellnessLectureDetailByWellnessLectureId(Long memberId, Long wellnessLectureId);
    List<WellnessLectureMobileDto> getWellnessLectureBookingDateListByWellnessClassId(Long wellnessClassId);
    List<WellnessLectureMobileDto> getWellnessLectureManageListByDate(Long memberId, ZonedDateTime startDateTime, ZonedDateTime endDateTime);
}
