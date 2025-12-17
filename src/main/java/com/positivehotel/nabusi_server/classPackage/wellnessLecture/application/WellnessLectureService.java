package com.positivehotel.nabusi_server.classPackage.wellnessLecture.application;

import com.positivehotel.nabusi_server.classPackage.wellnessLecture.application.dto.WellnessLectureDto;

import java.time.ZonedDateTime;
import java.util.List;

public interface WellnessLectureService {
    void createAll(List<WellnessLectureDto> wellnessLectureDtoList);
    List<WellnessLectureDto> getAllByWellnessClassIdList(List<Long> wellnessClassIdList);
    void update(WellnessLectureDto wellnessLectureDto);
    List<WellnessLectureDto> getAllByWellnessTicketManagementIdListIn(List<Long> wellnessTicketManagementIdList);
    List<WellnessLectureDto> getWellnessLectureByDateRange(ZonedDateTime startDateTime, ZonedDateTime endDateTime);
    WellnessLectureDto getById(Long id);
    void delete(Long id);
    void restore(Long id);
    List<WellnessLectureDto> getAllByTeacherIdList(List<Long> teacherIdList);
    List<WellnessLectureDto> getAllByIdList(List<Long> idList);
    List<WellnessLectureDto> getAllByCenterIdAndStartDateTimeBetweenAndIsDeleteFalse(Long centerId, ZonedDateTime startDateTime, ZonedDateTime endDateTime);
    List<WellnessLectureDto> getAllByCenterIdAndTeacherIdAndStartDateTimeBetweenAndIsDeleteFalse(Long centerId, Long teacherId, ZonedDateTime startDateTime, ZonedDateTime endDateTime);
    List<WellnessLectureDto> getAvailableBookingListByWellnessClassId(Long wellnessClassId);
    List<WellnessLectureDto> getAllByCenterIdAndIsDeleteFalse(Long centerId);
}
