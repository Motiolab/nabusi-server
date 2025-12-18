package com.motiolab.nabusi_server.classPackage.wellnessClass.ui;

import com.motiolab.nabusi_server.classPackage.wellnessClass.application.WellnessClassMobileService;
import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.WellnessClassMobileDto;
import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.response.GetAllWellnessClassMobileListByCenterIdResponse;
import com.motiolab.nabusi_server.util.WellnessLectureReviewUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class WellnessClassMobileController {
    private final WellnessClassMobileService wellnessClassMobileService;

    @GetMapping("/v1/mobile/wellness-class/list")
    public ResponseEntity<List<GetAllWellnessClassMobileListByCenterIdResponse>> getAllWellnessClassListByCenterId(final @RequestParam Long centerId) {
        final List<WellnessClassMobileDto> wellnessClassMobileDtoList = wellnessClassMobileService.getAllWellnessClassListByCenterId(centerId);

        final List<GetAllWellnessClassMobileListByCenterIdResponse> responseList = wellnessClassMobileDtoList.stream().map(wellnessClassMobileDto ->
                GetAllWellnessClassMobileListByCenterIdResponse.builder()
                        .id(wellnessClassMobileDto.getWellnessClassDto().getId())
                        .name(wellnessClassMobileDto.getWellnessClassDto().getName())
                        .description(wellnessClassMobileDto.getWellnessClassDto().getDescription())
                        .classImageUrl(wellnessClassMobileDto.getWellnessClassDto().getClassImageUrlList().get(0))
                        .maxReservationCnt(wellnessClassMobileDto.getWellnessClassDto().getMaxReservationCnt())
                        .teacherName(wellnessClassMobileDto.getTeacherDto().getName())
                        .room(wellnessClassMobileDto.getWellnessClassDto().getRoom())
                        .wellnessLectureTypeName(wellnessClassMobileDto.getWellnessLectureTypeDto().name())
                        .wellnessLectureTypeDescription(wellnessClassMobileDto.getWellnessLectureTypeDto().description())
                        .rating(WellnessLectureReviewUtils.calculateAverageRating(wellnessClassMobileDto.getWellnessLectureReviewDtoList()))
                        .reviewCnt(wellnessClassMobileDto.getWellnessLectureReviewDtoList() != null ? wellnessClassMobileDto.getWellnessLectureReviewDtoList().size() : null)
                        .build()
        ).toList();

        return ResponseEntity.ok(responseList);
    }
}
