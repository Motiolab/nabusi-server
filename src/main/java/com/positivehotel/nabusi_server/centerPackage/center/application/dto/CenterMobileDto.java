package com.positivehotel.nabusi_server.centerPackage.center.application.dto;

import com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.application.dto.WellnessLectureReviewDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CenterMobileDto {
    CenterDto centerDto;
    List<CenterRoomDto> centerRoomDtoList;
    List<CenterOpenInfoDto> centerOpenInfoDtoList;
    List<CenterContactNumberDto> centerContactNumberDtoList;
    List<WellnessLectureReviewDto> wellnessLectureReviewDtoList;
}
