package com.positivehotel.nabusi_server.util.AwsS3.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AwsS3FileNameGenerator {
    private static final String FILE_EXTENSION_SEPARATOR = ".";
    private static final String DATE_PREFIX = "/";
    private static final String FOLDER_PREFIX = "standard";

    public static String buildFileName(final String originalFileName) {
        final LocalDate now = LocalDate.now();
        final int year = now.getYear();
        final int month = now.getMonthValue();
        final int day = now.getDayOfMonth();

        final int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        final String fileExtension = originalFileName.substring(fileExtensionIndex);
        final String fileName = originalFileName.substring(0, fileExtensionIndex);

        return FOLDER_PREFIX + DATE_PREFIX + year + DATE_PREFIX + month + DATE_PREFIX + day + DATE_PREFIX + fileName + fileExtension;
    }
}
