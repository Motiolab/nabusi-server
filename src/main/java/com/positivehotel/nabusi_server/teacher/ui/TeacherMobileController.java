package com.positivehotel.nabusi_server.teacher.ui;

import com.positivehotel.nabusi_server.teacher.application.TeacherMobileService;
import com.positivehotel.nabusi_server.teacher.application.dto.TeacherMobileDto;
import com.positivehotel.nabusi_server.teacher.application.dto.response.GetTeacherMobileListResponse;
import com.positivehotel.nabusi_server.util.WellnessLectureReviewUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TeacherMobileController {
    private final TeacherMobileService teacherMobileService;

    @GetMapping("/v1/mobile/teacher/list")
    public ResponseEntity<List<GetTeacherMobileListResponse>> getTeacherListByCenterId(final @RequestParam Long centerId) {
        final List<TeacherMobileDto> teacherMobileDtoList = teacherMobileService.getTeacherListByCenterId(centerId);
        final List<GetTeacherMobileListResponse> responses = teacherMobileDtoList
                .stream()
                .map(teacherMobileDto -> GetTeacherMobileListResponse.builder()
                        .id(teacherMobileDto.getTeacherDto().getId())
                        .name(teacherMobileDto.getTeacherDto().getName())
                        .nickName(teacherMobileDto.getTeacherDto().getNickName())
                        .imageUrl(teacherMobileDto.getTeacherDto().getImageUrl())
                        .rating(teacherMobileDto.getWellnessLectureReviewDtoList() != null ? WellnessLectureReviewUtils.calculateAverageRating(teacherMobileDto.getWellnessLectureReviewDtoList()) : null)
                        .reviewCnt(teacherMobileDto.getWellnessLectureReviewDtoList() != null ? teacherMobileDto.getWellnessLectureReviewDtoList().size() : null)
                        .build()
                ).toList();

        return ResponseEntity.ok(responses);
    }
}