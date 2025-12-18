package com.motiolab.nabusi_server.classPackage.wellnessLecture.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;

public interface WellnessLectureRepository extends JpaRepository<WellnessLectureEntity, Long> {
    List<WellnessLectureEntity> findAllByWellnessClassIdIn(List<Long> wellnessClassIdList);

    @Query(value = "SELECT * FROM wellness_lecture " +
            "WHERE wellness_ticket_management_id_list REGEXP CONCAT('(^|,)', :wellnessTicketManagementIdList, '(,|$)')",
            nativeQuery = true)
    List<WellnessLectureEntity> findAllByIssuedWellnessTicketManageIdListFindInSet(String wellnessTicketManagementIdList);
    List<WellnessLectureEntity> findAllByStartDateTimeBetweenOrderByStartDateTimeAsc(ZonedDateTime startDateTime, ZonedDateTime endDateTime);
    List<WellnessLectureEntity> findAllByTeacherIdIn(List<Long> teacherIdList);
    List<WellnessLectureEntity> findAllByIdIn(List<Long> idList);
    List<WellnessLectureEntity> findAllByCenterIdAndStartDateTimeBetweenAndIsDeleteFalseOrderByStartDateTimeAsc(Long centerId, ZonedDateTime startDateTime, ZonedDateTime endDateTime);
    @Query(value = "SELECT wl.* FROM wellness_lecture wl " +
            "WHERE wl.wellness_class_id = :wellnessClassId " +
            "AND wl.is_delete = false " +
            "AND wl.start_date_time > :now", nativeQuery = true)
    List<WellnessLectureEntity> findAllByWellnessClassIdAndIsDeleteFalseAndStartDateTimeAfterNow(
            Long wellnessClassId,
            ZonedDateTime now
    );
    List<WellnessLectureEntity> findAllByCenterIdAndIsDeleteFalseOrderByStartDateTimeAsc(Long centerId);
    List<WellnessLectureEntity> findAllByCenterIdAndTeacherIdAndStartDateTimeBetweenAndIsDeleteFalseOrderByStartDateTimeAsc(Long centerId, Long teacherId, ZonedDateTime startDateTime, ZonedDateTime endDateTime);
}
