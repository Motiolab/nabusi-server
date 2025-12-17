package com.positivehotel.nabusi_server.teacher.application.dto;

import com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.application.dto.WellnessLectureReviewDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TeacherMobileDto {
    private TeacherDto teacherDto;
    private List<WellnessLectureReviewDto> wellnessLectureReviewDtoList;
}
