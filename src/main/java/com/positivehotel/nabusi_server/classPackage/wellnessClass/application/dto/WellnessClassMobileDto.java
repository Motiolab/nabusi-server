package com.positivehotel.nabusi_server.classPackage.wellnessClass.application.dto;

import com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.application.dto.WellnessLectureReviewDto;
import com.positivehotel.nabusi_server.classPackage.wellnessLectureType.application.dto.WellnessLectureTypeDto;
import com.positivehotel.nabusi_server.teacher.application.dto.TeacherDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class WellnessClassMobileDto {
    private WellnessClassDto wellnessClassDto;
    private List<WellnessLectureReviewDto> wellnessLectureReviewDtoList;
    private TeacherDto teacherDto;
    private WellnessLectureTypeDto wellnessLectureTypeDto;
}
