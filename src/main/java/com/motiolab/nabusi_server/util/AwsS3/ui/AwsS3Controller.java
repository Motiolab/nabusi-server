package com.motiolab.nabusi_server.util.AwsS3.ui;


import com.motiolab.nabusi_server.util.AwsS3.application.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    @PostMapping("/s3/upload")
    public ResponseEntity<String> uploadFile(final MultipartFile multipartFile) {
        String storedUrl = awsS3Service.uploadFile(multipartFile);
        return ResponseEntity.ok(storedUrl);
    }

}
