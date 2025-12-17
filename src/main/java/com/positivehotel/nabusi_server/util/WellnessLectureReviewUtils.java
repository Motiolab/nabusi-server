package com.positivehotel.nabusi_server.util;

import com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.application.dto.WellnessLectureReviewDto;

import java.util.List;

public class WellnessLectureReviewUtils {
    public static Double calculateAverageRating(List<WellnessLectureReviewDto> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }

        double averageRating = (double) reviews.stream().mapToLong(WellnessLectureReviewDto::getRating).sum() / reviews.size();
        String formattedRating = String.format("%.1f", averageRating);
        return Double.parseDouble(formattedRating);
    }
}
