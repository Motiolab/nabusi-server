package com.motiolab.nabusi_server.classPackage.wellnessLecture.application;

import com.motiolab.nabusi_server.classPackage.wellnessClass.application.WellnessClassService;
import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.WellnessClassDto;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.*;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.request.CreateWellnessLectureListWithWellnessClassAdminRequestV1;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.request.UpdateWellnessLectureAdminRequestV1;
import com.motiolab.nabusi_server.classPackage.wellnessLectureType.application.WellnessLectureTypeService;
import com.motiolab.nabusi_server.classPackage.wellnessLectureType.application.dto.WellnessLectureTypeDto;
import com.motiolab.nabusi_server.exception.customException.NotFoundException;
import com.motiolab.nabusi_server.teacher.application.TeacherService;
import com.motiolab.nabusi_server.teacher.application.dto.TeacherDto;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicket.application.WellnessTicketService;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicket.application.dtos.WellnessTicketDto;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketManagement.application.WellnessTicketManagementService;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketManagement.application.dto.WellnessTicketManagementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WellnessLectureAdminServiceImpl implements WellnessLectureAdminService{
    private final WellnessLectureService wellnessLectureService;
    private final WellnessClassService wellnessClassService;
    private final CalcZoneDateTime calcZoneDateTime;
    private final TeacherService teacherService;
    private final WellnessLectureTypeService wellnessLectureTypeService;
    private final WellnessTicketManagementService wellnessTicketManagementService;
    private final WellnessTicketService wellnessTicketService;

    @Override
    public void createWellnessLectureListWithWellnessClass(CreateWellnessLectureListWithWellnessClassAdminRequestV1 createWellnessLectureListWithWellnessClassAdminRequestV1) {
        final WellnessClassDto wellnessClassDto = WellnessClassDto.builder()
                .id(createWellnessLectureListWithWellnessClassAdminRequestV1.getWellnessClassId())
                .description(createWellnessLectureListWithWellnessClassAdminRequestV1.getDescription())
                .centerId(createWellnessLectureListWithWellnessClassAdminRequestV1.getCenterId())
                .maxReservationCnt(createWellnessLectureListWithWellnessClassAdminRequestV1.getMaxReservationCnt())
                .registerId(createWellnessLectureListWithWellnessClassAdminRequestV1.getRegistrantId())
                .room(createWellnessLectureListWithWellnessClassAdminRequestV1.getRoom())
                .classImageUrlList(createWellnessLectureListWithWellnessClassAdminRequestV1.getClassImageUrlList())
                .teacherId(createWellnessLectureListWithWellnessClassAdminRequestV1.getTeacherId())
                .wellnessLectureTypeId(createWellnessLectureListWithWellnessClassAdminRequestV1.getWellnessLectureTypeId())
                .isDelete(false)
                .wellnessTicketManagementIdList(createWellnessLectureListWithWellnessClassAdminRequestV1.getWellnessTicketManagementIdList())
                .build();

        final Boolean isSuccessUpdate = wellnessClassService.update(wellnessClassDto);

        if (!isSuccessUpdate) {
            wellnessClassDto.setId(null);
            wellnessClassService.create(wellnessClassDto);
        }

        final List<TimeWithDay> timeWithDayList = createWellnessLectureListWithWellnessClassAdminRequestV1.getTimeRangeWithDays().stream().map(timeRangeWithDay -> TimeWithDay.builder().startTime(timeRangeWithDay.getStartTime()).endTime(timeRangeWithDay.getEndTime()).dayOfWeek(timeRangeWithDay.getDayOfWeek()).build()).toList();
        final RangeZoneDateTime rangeZoneDateTime = RangeZoneDateTime.builder().startDateTime(createWellnessLectureListWithWellnessClassAdminRequestV1.getStartDateTime()).endDateTime(createWellnessLectureListWithWellnessClassAdminRequestV1.getEndDateTime()).build();
        final List<CalcZoneDateTimeListResponse> calcDateListResponseList = calcZoneDateTime.getByRangeDate(rangeZoneDateTime, timeWithDayList);

        final List<WellnessLectureDto> wellnessLectureEntityList = calcDateListResponseList.stream().map(calcDateListResponse -> WellnessLectureDto.builder()
                .name(createWellnessLectureListWithWellnessClassAdminRequestV1.getName())
                .description(createWellnessLectureListWithWellnessClassAdminRequestV1.getDescription())
                .centerId(createWellnessLectureListWithWellnessClassAdminRequestV1.getCenterId())
                .maxReservationCnt(createWellnessLectureListWithWellnessClassAdminRequestV1.getMaxReservationCnt())
                .registerId(createWellnessLectureListWithWellnessClassAdminRequestV1.getRegistrantId())
                .room(createWellnessLectureListWithWellnessClassAdminRequestV1.getRoom())
                .lectureImageUrlList(createWellnessLectureListWithWellnessClassAdminRequestV1.getClassImageUrlList())
                .price(createWellnessLectureListWithWellnessClassAdminRequestV1.getPrice())
                .wellnessClassId(createWellnessLectureListWithWellnessClassAdminRequestV1.getWellnessClassId())
                .teacherId(createWellnessLectureListWithWellnessClassAdminRequestV1.getTeacherId())
                .wellnessLectureTypeId(createWellnessLectureListWithWellnessClassAdminRequestV1.getWellnessLectureTypeId())
                .startDateTime(calcDateListResponse.getStartDateTime())
                .endDateTime(calcDateListResponse.getEndDateTime())
                .isDelete(false)
                .wellnessTicketManagementIdList(createWellnessLectureListWithWellnessClassAdminRequestV1.getWellnessTicketManagementIdList())
                .build()).toList();

        wellnessLectureService.createAll(wellnessLectureEntityList);
    }

    @Override
    public List<WellnessLectureAdminDto> getWellnessLectureListByStartDate(ZonedDateTime startDate) {
        final ZonedDateTime startDateTime = startDate.with(LocalTime.MIN);
        final ZonedDateTime endDateTime = startDate.with(LocalTime.MAX);

        final List<WellnessLectureDto> wellnessLectureDtoList = wellnessLectureService.getWellnessLectureByDateRange(startDateTime, endDateTime);

        final List<Long> teacherIdList = wellnessLectureDtoList.stream().map(WellnessLectureDto::getTeacherId).distinct().toList();
        final List<TeacherDto> teacherDtoList = teacherService.getAllByIdList(teacherIdList);

        final List<Long> wellnessLectureTypeIdList = wellnessLectureDtoList.stream().map(WellnessLectureDto::getWellnessLectureTypeId).distinct().toList();
        final List<WellnessLectureTypeDto> wellnessLectureTypeDtoList =wellnessLectureTypeService.getAllByIdList(wellnessLectureTypeIdList);

        return wellnessLectureDtoList.stream().map(wellnessLectureDto -> WellnessLectureAdminDto.builder()
                        .wellnessLectureDto(wellnessLectureDto)
                        .teacherDto(teacherDtoList.stream().filter(teacherDto -> teacherDto.getId().equals(wellnessLectureDto.getTeacherId())).findFirst().orElseThrow(() -> new RuntimeException("Error: Teacher is not found. id: " + wellnessLectureDto.getTeacherId())))
                        .wellnessLectureTypeDto(wellnessLectureTypeDtoList.stream().filter(wellnessLectureTypeDto -> wellnessLectureTypeDto.id().equals(wellnessLectureDto.getWellnessLectureTypeId())).findFirst().orElseThrow(() -> new RuntimeException("Error: WellnessLectureType is not found. id: " + wellnessLectureDto.getWellnessLectureTypeId())))
                        .build())
                .toList();
    }

    @Override
    public WellnessLectureAdminDto getWellnessLectureDetailById(Long id) {
        final WellnessLectureDto wellnessLectureDto = wellnessLectureService.getById(id);
        if(wellnessLectureDto == null) {
            throw new NotFoundException(WellnessLectureDto.class, id);
        }

        final TeacherDto teacherDto = teacherService.getById(wellnessLectureDto.getTeacherId());
        if(teacherDto == null) {
            throw new NotFoundException(TeacherDto.class, wellnessLectureDto.getTeacherId());
        }

        final WellnessLectureTypeDto wellnessLectureTypeDto = wellnessLectureTypeService.getById(wellnessLectureDto.getWellnessLectureTypeId());
        if(wellnessLectureTypeDto == null) {
            throw new NotFoundException(WellnessLectureTypeDto.class, wellnessLectureDto.getWellnessLectureTypeId());
        }

        final List<WellnessTicketManagementDto> wellnessTicketManagementDtoList = wellnessTicketManagementService.getAllByIdList(wellnessLectureDto.getWellnessTicketManagementIdList());
        final List<Long> wellnessTicketIdList = wellnessTicketManagementDtoList.stream().map(WellnessTicketManagementDto::getWellnessTicketId).distinct().toList();
        final List<WellnessTicketDto> wellnessTicketDtoList = wellnessTicketService.getAllById(wellnessTicketIdList);

        final List<WellnessLectureAdminDto.WellnessTicketExtension> wellnessTicketExtensionList = wellnessTicketManagementDtoList
                .stream()
                .map(wellnessTicketManagementDto -> WellnessLectureAdminDto.WellnessTicketExtension.builder()
                        .wellnessTicketManagementDto(wellnessTicketManagementDto)
                        .wellnessTicketDto(wellnessTicketDtoList.stream().filter(wellnessTicketDto -> wellnessTicketDto.getId().equals(wellnessTicketManagementDto.getWellnessTicketId())).findFirst().orElseThrow(() -> new NotFoundException(WellnessTicketDto.class, wellnessTicketManagementDto.getWellnessTicketId())))
                        .build())
                .toList();

        return WellnessLectureAdminDto.builder()
                .wellnessLectureDto(wellnessLectureDto)
                .teacherDto(teacherDto)
                .wellnessLectureTypeDto(wellnessLectureTypeDto)
                .wellnessTicketExtensionList(wellnessTicketExtensionList)
                .build();
    }

    @Override
    public void deleteWellnessLectureById(Long id, Boolean isSendNoti) {
        wellnessLectureService.delete(id);
        //TODO
        //예약된 정보 삭제
        //알림 전송하기
    }

    @Override
    public void restoreWellnessLectureById(Long id) {
        wellnessLectureService.restore(id);
    }

    @Override
    public void updateWellnessLecture(UpdateWellnessLectureAdminRequestV1 updateWellnessLectureAdminRequestV1) {
        final WellnessLectureDto wellnessLectureDto = WellnessLectureDto.builder()
                .id(updateWellnessLectureAdminRequestV1.getId())
                .name(updateWellnessLectureAdminRequestV1.getName())
                .description(updateWellnessLectureAdminRequestV1.getDescription())
                .centerId(updateWellnessLectureAdminRequestV1.getCenterId())
                .maxReservationCnt(updateWellnessLectureAdminRequestV1.getMaxReservationCnt())
                .room(updateWellnessLectureAdminRequestV1.getRoom())
                .lectureImageUrlList(updateWellnessLectureAdminRequestV1.getLectureImageUrlList())
                .price(updateWellnessLectureAdminRequestV1.getPrice())
                .teacherId(updateWellnessLectureAdminRequestV1.getTeacherId())
                .wellnessLectureTypeId(updateWellnessLectureAdminRequestV1.getWellnessLectureTypeId())
                .startDateTime(updateWellnessLectureAdminRequestV1.getStartDateTime())
                .endDateTime(updateWellnessLectureAdminRequestV1.getEndDateTime())
                .wellnessTicketManagementIdList(updateWellnessLectureAdminRequestV1.getWellnessTicketManagementIdList())
                .build();

        wellnessLectureService.update(wellnessLectureDto);
    }
}
