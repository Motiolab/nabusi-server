package com.motiolab.nabusi_server.util.AwsS3.application;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AwsS3Service {
    String uploadFile(MultipartFile multipartFile);
    List<String> uploadFileList(List<MultipartFile> multipartFileList);
}
