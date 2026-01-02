package com.motiolab.nabusi_server.classPackage.wellnessLectureReview.ui;

import com.motiolab.nabusi_server.argumentResolver.MemberId;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.WellnessLectureReviewMobileService;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.WellnessLectureReviewDto;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.WellnessLectureReviewMobileDto;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.request.CreateWellnessLectureReviewMobileRequest;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.request.UpdateWellnessLectureReviewMobileRequest;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.response.CreateWellnessLectureReviewResponse;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.response.GetWellnessLectureReviewByIdMobileResponse;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.response.GetWellnessLectureReviewListByTypeAndIdResponse;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.response.UpdateWellnessLectureReviewResponse;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReviewComment.application.dto.request.CreateWellnessLectureReviewCommentMobileRequest;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReviewComment.application.dto.request.DeleteWellnessLectureReviewCommentMobileRequest;
import com.motiolab.nabusi_server.util.WellnessLectureReviewUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class WellnessLectureReviewMobileController {
        private final WellnessLectureReviewMobileService wellnessLectureReviewMobileService;

        @PostMapping("/v1/mobile/wellness-lecture-review/create")
        public ResponseEntity<CreateWellnessLectureReviewResponse> createWellnessLectureReview(@MemberId Long memberId,
                        @RequestBody CreateWellnessLectureReviewMobileRequest createWellnessLectureReviewMobileRequest) {
                wellnessLectureReviewMobileService.createWellnessLectureReview(memberId,
                                createWellnessLectureReviewMobileRequest);
                return ResponseEntity.ok(CreateWellnessLectureReviewResponse.builder().success(true).build());
        }

        @GetMapping("/v1/mobile/wellness-lecture-review/{wellnessLectureReviewId}")
        public ResponseEntity<GetWellnessLectureReviewByIdMobileResponse> getWellnessLectureReviewById(
                        @PathVariable Long wellnessLectureReviewId) {
                final WellnessLectureReviewMobileDto wellnessLectureReviewMobileDto = wellnessLectureReviewMobileService
                                .getWellnessLectureReviewById(wellnessLectureReviewId);
                final GetWellnessLectureReviewByIdMobileResponse getWellnessLectureReviewByIdMobileResponse = GetWellnessLectureReviewByIdMobileResponse
                                .builder()
                                .id(wellnessLectureReviewMobileDto.getWellnessLectureReviewDto().getId())
                                .content(wellnessLectureReviewMobileDto.getWellnessLectureReviewDto().getContent())
                                .isPrivate(wellnessLectureReviewMobileDto.getWellnessLectureReviewDto().getIsPrivate())
                                .rating(wellnessLectureReviewMobileDto.getWellnessLectureReviewDto().getRating())
                                .build();

                return ResponseEntity.ok(getWellnessLectureReviewByIdMobileResponse);
        }

        @PutMapping("/v1/mobile/wellness-lecture-review/update")
        public ResponseEntity<UpdateWellnessLectureReviewResponse> updateWellnessLectureReview(@MemberId Long memberId,
                        @RequestBody UpdateWellnessLectureReviewMobileRequest updateWellnessLectureReviewMobileRequest) {
                wellnessLectureReviewMobileService.updateWellnessLectureReview(memberId,
                                updateWellnessLectureReviewMobileRequest);
                return ResponseEntity.ok(UpdateWellnessLectureReviewResponse.builder().success(true).build());
        }

        @PostMapping("/v1/mobile/wellness-lecture-review/comment/create")
        public ResponseEntity<Boolean> createComment(@MemberId Long memberId,
                        @RequestBody CreateWellnessLectureReviewCommentMobileRequest createWellnessLectureReviewCommentMobileRequest) {
                wellnessLectureReviewMobileService.createComment(memberId,
                                createWellnessLectureReviewCommentMobileRequest);
                return ResponseEntity.ok(true);
        }

        @PostMapping("/v1/mobile/wellness-lecture-review/comment/delete")
        public ResponseEntity<Boolean> deleteComment(@MemberId Long memberId, @RequestBody DeleteWellnessLectureReviewCommentMobileRequest deleteWellnessLectureReviewCommentMobileRequest) {
                wellnessLectureReviewMobileService.deleteComment(memberId, deleteWellnessLectureReviewCommentMobileRequest);
                return ResponseEntity.ok(true);
        }

        @GetMapping("/v1/mobile/wellness-lecture-review/{type}/{id}")
        public ResponseEntity<GetWellnessLectureReviewListByTypeAndIdResponse> getWellnessLectureReviewListByTypeAndId(
                        @MemberId Long memberId, @PathVariable String type, @PathVariable Long id) {
                final List<WellnessLectureReviewMobileDto> wellnessLectureReviewMobileDtoList = wellnessLectureReviewMobileService
                                .getWellnessLectureReviewListByTypeAndId(memberId, type, id);
                final Integer fiveRatingCnt = wellnessLectureReviewMobileDtoList.stream()
                                .filter(dto -> dto.getWellnessLectureReviewDto().getRating() == 5).toList().size();
                final Integer fourRatingCnt = wellnessLectureReviewMobileDtoList.stream()
                                .filter(dto -> dto.getWellnessLectureReviewDto().getRating() == 4).toList().size();
                final Integer threeRatingCnt = wellnessLectureReviewMobileDtoList.stream()
                                .filter(dto -> dto.getWellnessLectureReviewDto().getRating() == 3).toList().size();
                final Integer twoRatingCnt = wellnessLectureReviewMobileDtoList.stream()
                                .filter(dto -> dto.getWellnessLectureReviewDto().getRating() == 2).toList().size();
                final Integer oneRatingCnt = wellnessLectureReviewMobileDtoList.stream()
                                .filter(dto -> dto.getWellnessLectureReviewDto().getRating() == 1).toList().size();
                final List<WellnessLectureReviewDto> wellnessLectureReviewDtoList = wellnessLectureReviewMobileDtoList
                                .stream()
                                .map(WellnessLectureReviewMobileDto::getWellnessLectureReviewDto).toList();
                final Double avrageRating = WellnessLectureReviewUtils
                                .calculateAverageRating(wellnessLectureReviewDtoList);

                final GetWellnessLectureReviewListByTypeAndIdResponse response = GetWellnessLectureReviewListByTypeAndIdResponse
                                .builder()
                                .avrageRating(avrageRating)
                                .fiveRatingCnt(fiveRatingCnt)
                                .fourRatingCnt(fourRatingCnt)
                                .threeRatingCnt(threeRatingCnt)
                                .twoRatingCnt(twoRatingCnt)
                                .oneRatingCnt(oneRatingCnt)
                                .wellnessLectureReviewResponseList(wellnessLectureReviewMobileDtoList
                                                .stream()
                                                .filter(wellnessLectureReviewMobileDto -> !wellnessLectureReviewMobileDto
                                                                .getWellnessLectureReviewDto().getIsPrivate())
                                                .map(wellnessLectureReviewMobileDto -> GetWellnessLectureReviewListByTypeAndIdResponse.WellnessLectureReviewResponse
                                                                .builder()
                                                                .id(wellnessLectureReviewMobileDto
                                                                                .getWellnessLectureReviewDto().getId())
                                                                .content(wellnessLectureReviewMobileDto
                                                                                .getWellnessLectureReviewDto()
                                                                                .getContent())
                                                                .rating(wellnessLectureReviewMobileDto
                                                                                .getWellnessLectureReviewDto()
                                                                                .getRating())
                                                                .memberId(wellnessLectureReviewMobileDto
                                                                                .getMemberDtoExtension().getMemberDto()
                                                                                .getId())
                                                                .memberCheckInCnt(wellnessLectureReviewMobileDto
                                                                                .getMemberDtoExtension()
                                                                                .getReservationDtoList().size())
                                                                .memberReviewCnt(wellnessLectureReviewMobileDto
                                                                                .getMemberDtoExtension()
                                                                                .getWellnessLectureReviewDtoList()
                                                                                .size())
                                                                .isCreateCommentAvailable(wellnessLectureReviewMobileDto
                                                                                .getIsCreateCommentAvailable())
                                                                .wellnessLectureReviewCommentResponseList(
                                                                                wellnessLectureReviewMobileDto
                                                                                                .getWellnessLectureReviewCommentDtoList()
                                                                                                .stream()
                                                                                                .filter(wellnessLectureReviewCommentDto -> !wellnessLectureReviewCommentDto
                                                                                                                .getIsDelete())
                                                                                                .map(wellnessLectureReviewCommentDto -> GetWellnessLectureReviewListByTypeAndIdResponse.WellnessLectureReviewCommentResponse
                                                                                                                .builder()
                                                                                                                .id(wellnessLectureReviewCommentDto
                                                                                                                                .getId())
                                                                                                                .content(wellnessLectureReviewCommentDto
                                                                                                                                .getContent())
                                                                                                                .memberId(wellnessLectureReviewCommentDto
                                                                                                                                .getMemberId())
                                                                                                                .isDeleteAvailable(
                                                                                                                                Objects.equals(wellnessLectureReviewCommentDto
                                                                                                                                                .getMemberId(),
                                                                                                                                                memberId))
                                                                                                                .build())
                                                                                                .toList())
                                                                .build())
                                                .toList())
                                .build();

                return ResponseEntity.ok(response);
        }
}
