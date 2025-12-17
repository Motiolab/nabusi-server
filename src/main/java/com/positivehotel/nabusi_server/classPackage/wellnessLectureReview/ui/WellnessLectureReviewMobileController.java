package com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.ui;

import com.positivehotel.nabusi_server.argumentResolver.MemberId;
import com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.application.WellnessLectureReviewMobileService;
import com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.application.dto.WellnessLectureReviewDto;
import com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.application.dto.WellnessLectureReviewMobileDto;
import com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.application.dto.request.CreateWellnessLectureReviewMobileRequest;
import com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.application.dto.request.UpdateWellnessLectureReviewMobileRequest;
import com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.application.dto.response.CreateWellnessLectureReviewResponse;
import com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.application.dto.response.GetWellnessLectureReviewByIdMobileResponse;
import com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.application.dto.response.GetWellnessLectureReviewListByTypeAndIdResponse;
import com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.application.dto.response.UpdateWellnessLectureReviewResponse;
import com.positivehotel.nabusi_server.util.WellnessLectureReviewUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class WellnessLectureReviewMobileController {
    private final WellnessLectureReviewMobileService wellnessLectureReviewMobileService;

    @PostMapping("/v1/mobile/wellness-lecture-review/create")
    public ResponseEntity<CreateWellnessLectureReviewResponse> createWellnessLectureReview(@MemberId Long memberId, @RequestBody CreateWellnessLectureReviewMobileRequest createWellnessLectureReviewMobileRequest) {
        wellnessLectureReviewMobileService.createWellnessLectureReview(memberId, createWellnessLectureReviewMobileRequest);
        return ResponseEntity.ok(CreateWellnessLectureReviewResponse.builder().success(true).build());
    }

    @GetMapping("/v1/mobile/wellness-lecture-review/{wellnessLectureReviewId}")
    public ResponseEntity<GetWellnessLectureReviewByIdMobileResponse> getWellnessLectureReviewById(@PathVariable Long wellnessLectureReviewId) {
        final WellnessLectureReviewMobileDto wellnessLectureReviewMobileDto = wellnessLectureReviewMobileService.getWellnessLectureReviewById(wellnessLectureReviewId);
        final GetWellnessLectureReviewByIdMobileResponse getWellnessLectureReviewByIdMobileResponse = GetWellnessLectureReviewByIdMobileResponse.builder()
                .id(wellnessLectureReviewMobileDto.getWellnessLectureReviewDto().getId())
                .content(wellnessLectureReviewMobileDto.getWellnessLectureReviewDto().getContent())
                .isPrivate(wellnessLectureReviewMobileDto.getWellnessLectureReviewDto().getIsPrivate())
                .rating(wellnessLectureReviewMobileDto.getWellnessLectureReviewDto().getRating())
                .build();

        return ResponseEntity.ok(getWellnessLectureReviewByIdMobileResponse);
    }

    @PutMapping("/v1/mobile/wellness-lecture-review/update")
    public ResponseEntity<UpdateWellnessLectureReviewResponse> updateWellnessLectureReview(@MemberId Long memberId, @RequestBody UpdateWellnessLectureReviewMobileRequest updateWellnessLectureReviewMobileRequest) {
        wellnessLectureReviewMobileService.updateWellnessLectureReview(memberId, updateWellnessLectureReviewMobileRequest);
        return ResponseEntity.ok(UpdateWellnessLectureReviewResponse.builder().success(true).build());
    }

    @GetMapping("/v1/mobile/wellness-lecture-review/{type}/{id}")
    public ResponseEntity<GetWellnessLectureReviewListByTypeAndIdResponse> getWellnessLectureReviewListByTypeAndId(@PathVariable String type, @PathVariable Long id) {
        final List<WellnessLectureReviewMobileDto> wellnessLectureReviewMobileDtoList = wellnessLectureReviewMobileService.getWellnessLectureReviewListByTypeAndId(type, id);
        final Integer fiveRatingCnt = wellnessLectureReviewMobileDtoList.stream().filter(dto -> dto.getWellnessLectureReviewDto().getRating() == 5).toList().size();
        final Integer fourRatingCnt = wellnessLectureReviewMobileDtoList.stream().filter(dto -> dto.getWellnessLectureReviewDto().getRating() == 4).toList().size();
        final Integer threeRatingCnt = wellnessLectureReviewMobileDtoList.stream().filter(dto -> dto.getWellnessLectureReviewDto().getRating() == 3).toList().size();
        final Integer twoRatingCnt = wellnessLectureReviewMobileDtoList.stream().filter(dto -> dto.getWellnessLectureReviewDto().getRating() == 2).toList().size();
        final Integer oneRatingCnt = wellnessLectureReviewMobileDtoList.stream().filter(dto -> dto.getWellnessLectureReviewDto().getRating() == 1).toList().size();
        final List<WellnessLectureReviewDto> wellnessLectureReviewDtoList = wellnessLectureReviewMobileDtoList.stream().map(WellnessLectureReviewMobileDto::getWellnessLectureReviewDto).toList();
        final Double avrageRating = WellnessLectureReviewUtils.calculateAverageRating(wellnessLectureReviewDtoList);

        final GetWellnessLectureReviewListByTypeAndIdResponse response = GetWellnessLectureReviewListByTypeAndIdResponse.builder()
                .avrageRating(avrageRating)
                .fiveRatingCnt(fiveRatingCnt)
                .fourRatingCnt(fourRatingCnt)
                .threeRatingCnt(threeRatingCnt)
                .twoRatingCnt(twoRatingCnt)
                .oneRatingCnt(oneRatingCnt)
                .wellnessLectureReviewResponseList(wellnessLectureReviewMobileDtoList
                        .stream()
                        .filter(wellnessLectureReviewMobileDto -> !wellnessLectureReviewMobileDto.getWellnessLectureReviewDto().getIsPrivate())
                        .map(wellnessLectureReviewMobileDto -> GetWellnessLectureReviewListByTypeAndIdResponse.WellnessLectureReviewResponse.builder()
                                .id(wellnessLectureReviewMobileDto.getWellnessLectureReviewDto().getId())
                                .content(wellnessLectureReviewMobileDto.getWellnessLectureReviewDto().getContent())
                                .rating(wellnessLectureReviewMobileDto.getWellnessLectureReviewDto().getRating())
                                .memberId(wellnessLectureReviewMobileDto.getMemberDtoExtension().getMemberDto().getId())
                                .memberCheckInCnt(wellnessLectureReviewMobileDto.getMemberDtoExtension().getReservationDtoList().size())
                                .memberReviewCnt(wellnessLectureReviewMobileDto.getMemberDtoExtension().getWellnessLectureReviewDtoList().size())
                                .build())
                        .toList())
                .build();

        return ResponseEntity.ok(response);
    }
}
