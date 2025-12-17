package com.positivehotel.nabusi_server.teacher.application;

import com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.application.WellnessLectureReviewService;
import com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.application.dto.WellnessLectureReviewDto;
import com.positivehotel.nabusi_server.teacher.application.dto.TeacherDto;
import com.positivehotel.nabusi_server.teacher.application.dto.TeacherMobileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherMobileServiceImpl implements TeacherMobileService {
    private final TeacherService teacherService;
    private final WellnessLectureReviewService wellnessLectureReviewService;

    @Override
    public List<TeacherMobileDto> getTeacherListByCenterId(Long centerId) {
        final List<TeacherDto> teacherDtoList = teacherService.getAllByCenterId(centerId);
        final List<Long> teacherIdList = teacherDtoList.stream().map(TeacherDto::getId).toList();
        final List<WellnessLectureReviewDto> wellnessLectureReviewDtoList = wellnessLectureReviewService.getAllByTeacherIdList(teacherIdList);

        return teacherDtoList
                .stream()
                .map(teacherDto -> TeacherMobileDto.builder()
                        .teacherDto(teacherDto)
                        .wellnessLectureReviewDtoList(wellnessLectureReviewDtoList
                                .stream()
                                .filter(wellnessLectureReviewDto -> wellnessLectureReviewDto.getTeacherId().equals(teacherDto.getId()))
                                .toList()
                        )
                        .build())
                .toList();
    }
}
