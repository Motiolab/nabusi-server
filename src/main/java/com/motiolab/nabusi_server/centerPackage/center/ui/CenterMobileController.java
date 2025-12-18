package com.motiolab.nabusi_server.centerPackage.center.ui;

import com.motiolab.nabusi_server.centerPackage.center.application.CenterMobileService;
import com.motiolab.nabusi_server.centerPackage.center.application.dto.CenterMobileDto;
import com.motiolab.nabusi_server.centerPackage.center.application.dto.response.GetMobileCenterActiveListResponseV1;
import com.motiolab.nabusi_server.util.WellnessLectureReviewUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CenterMobileController {
    private final CenterMobileService centerMobileService;

    @GetMapping("/v1/mobile/center/list")
    public ResponseEntity<List<GetMobileCenterActiveListResponseV1>> getMobileCenterActiveList() {
        final List<CenterMobileDto> centerMobileDtoList = centerMobileService.getMobileCenterActiveList();
        final List<GetMobileCenterActiveListResponseV1> responseList = centerMobileDtoList
                .stream()
                .map(centerMobileDto -> GetMobileCenterActiveListResponseV1.builder()
                        .id(centerMobileDto.getCenterDto().getId())
                        .name(centerMobileDto.getCenterDto().getName())
                        .address(centerMobileDto.getCenterDto().getAddress())
                        .detailAddress(centerMobileDto.getCenterDto().getDetailAddress())
                        .description(centerMobileDto.getCenterDto().getDescription())
                        .imageUrlList(centerMobileDto.getCenterDto().getImageUrlList())
                        .rating(WellnessLectureReviewUtils.calculateAverageRating(centerMobileDto.getWellnessLectureReviewDtoList()))
                        .reviewCnt(centerMobileDto.getWellnessLectureReviewDtoList() != null ? centerMobileDto.getWellnessLectureReviewDtoList().size() : 0)
                        .build()
                ).toList();

        return ResponseEntity.ok(responseList);
    }
}
