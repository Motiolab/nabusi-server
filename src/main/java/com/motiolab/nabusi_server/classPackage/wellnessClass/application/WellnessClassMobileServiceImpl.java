package com.motiolab.nabusi_server.classPackage.wellnessClass.application;

import com.motiolab.nabusi_server.centerPackage.center.application.CenterRoomService;
import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.WellnessClassDto;
import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.WellnessClassMobileDto;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.WellnessLectureReviewService;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.WellnessLectureReviewDto;
import com.motiolab.nabusi_server.classPackage.wellnessLectureType.application.WellnessLectureTypeService;
import com.motiolab.nabusi_server.classPackage.wellnessLectureType.application.dto.WellnessLectureTypeDto;
import com.motiolab.nabusi_server.exception.customException.NotFoundException;
import com.motiolab.nabusi_server.teacher.application.TeacherService;
import com.motiolab.nabusi_server.teacher.application.dto.TeacherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WellnessClassMobileServiceImpl implements WellnessClassMobileService{
    private final WellnessClassService wellnessClassService;
    private final WellnessLectureReviewService wellnessLectureReviewService;
    private final TeacherService teacherService;
    private final CenterRoomService centerRoomService;
    private final WellnessLectureTypeService wellnessLectureTypeService;

    @Override
    public List<WellnessClassMobileDto> getAllWellnessClassListByCenterId(Long centerId) {
        final List<WellnessClassDto> wellnessClassDtoList = wellnessClassService.getAllByCenterIdAndIsDeleteFalse(centerId);
        final List<Long> wellnessClassIdList = wellnessClassDtoList.stream().map(WellnessClassDto::getId).toList();
        final List<Long> teacherIdList = wellnessClassDtoList.stream().map(WellnessClassDto::getTeacherId).distinct().toList();
        final List<Long> wellnessLectureTypeIdList = wellnessClassDtoList.stream().map(WellnessClassDto::getWellnessLectureTypeId).distinct().toList();

        final List<TeacherDto> teacherDtoList = teacherService.getAllByIdList(teacherIdList);
        final List<WellnessLectureReviewDto> wellnessLectureReviewDtoList = wellnessLectureReviewService.getAllByWellnessClassIdList(wellnessClassIdList);
        final List<WellnessLectureTypeDto> wellnessLectureTypeDtoList = wellnessLectureTypeService.getAllByIdList(wellnessLectureTypeIdList);

        return wellnessClassDtoList.stream().map(wellnessClassDto ->
                WellnessClassMobileDto.builder()
                        .wellnessClassDto(wellnessClassDto)
                        .wellnessLectureReviewDtoList(wellnessLectureReviewDtoList
                                .stream()
                                .filter(wellnessLectureReviewDto -> Objects.equals(wellnessLectureReviewDto.getWellnessClassId(), wellnessClassDto.getId()))
                                .toList())
                        .teacherDto(teacherDtoList
                                .stream()
                                .filter(teacherDto -> Objects.equals(teacherDto.getId(), wellnessClassDto.getTeacherId()))
                                .findFirst()
                                .orElseThrow(() -> new NotFoundException(TeacherDto.class, wellnessClassDto.getTeacherId()))
                        )
                        .wellnessLectureTypeDto(wellnessLectureTypeDtoList
                                .stream()
                                .filter(wellnessLectureTypeDto -> Objects.equals(wellnessLectureTypeDto.id(), wellnessClassDto.getWellnessLectureTypeId()))
                                .findFirst()
                                .orElse(null)
                        )
                        .build()
        ).toList();
    }
}
