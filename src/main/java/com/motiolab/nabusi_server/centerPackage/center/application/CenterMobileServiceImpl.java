package com.motiolab.nabusi_server.centerPackage.center.application;

import com.motiolab.nabusi_server.centerPackage.center.application.dto.CenterDto;
import com.motiolab.nabusi_server.centerPackage.center.application.dto.CenterMobileDto;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.WellnessLectureReviewService;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.WellnessLectureReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CenterMobileServiceImpl implements CenterMobileService{
    private final CenterService centerService;
    private final WellnessLectureReviewService wellnessLectureReviewService;

    @Override
    public List<CenterMobileDto> getMobileCenterActiveList() {
        final List<CenterDto> centerDtoList =  centerService.getAllByIsActiveTrue();
        final List<Long> centerIdList = centerDtoList.stream().map(CenterDto::getId).toList();
        final List<WellnessLectureReviewDto> wellnessLectureReviewDtoList = wellnessLectureReviewService.getAllByCenterIdList(centerIdList);

        return centerDtoList
                .stream()
                .map(centerDto -> {
                            final List<WellnessLectureReviewDto> targetWellnessLectureReviewDtoList = wellnessLectureReviewDtoList
                                    .stream()
                                    .filter(wellnessLectureReviewDto -> wellnessLectureReviewDto.getCenterId().equals(centerDto.getId()))
                                    .toList();
                            return CenterMobileDto.builder()
                                    .centerDto(centerDto)
                                    .wellnessLectureReviewDtoList(targetWellnessLectureReviewDtoList)
                                    .build();
                        }
                )
                .toList();
    }
}
