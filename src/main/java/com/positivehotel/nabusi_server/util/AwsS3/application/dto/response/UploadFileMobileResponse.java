package com.positivehotel.nabusi_server.util.AwsS3.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileMobileResponse {
    private String url;
    private Boolean success;
}
