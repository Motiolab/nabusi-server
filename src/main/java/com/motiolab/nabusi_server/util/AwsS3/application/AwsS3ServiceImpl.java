package com.motiolab.nabusi_server.util.AwsS3.application;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.motiolab.nabusi_server.util.AwsS3.config.AwsS3Config;
import com.motiolab.nabusi_server.util.AwsS3.utils.AwsS3FileNameGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3ServiceImpl implements AwsS3Service{

    private final AwsS3Config awsS3Config;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.image.dns.url}")
    private String imageDnsUrl;

    @Value("${cloud.aws.image.saved.url}")
    private String imageSavedUrl;

    @Override
    public String uploadFile(final MultipartFile multipartFile) {
        validateFileExists(multipartFile);

        final String fileName = AwsS3FileNameGenerator.buildFileName(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        final long size = multipartFile.getSize();

        final ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(size);

        final AmazonS3Client awsS3Client = awsS3Config.amazonS3Client();
        try {
            awsS3Client.putObject(new PutObjectRequest(bucketName, fileName, multipartFile.getInputStream(),  objectMetadata));
        } catch (AmazonServiceException e) {
            log.error("Exception : ", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final String storedImageUrlAwsS3 = awsS3Client.getUrl(bucketName, fileName).toString();
        return storedImageUrlAwsS3.replace(imageSavedUrl, imageDnsUrl);
    }

    @Override
    public List<String> uploadFileList(final List<MultipartFile> multipartFileList) {
        return multipartFileList.stream().map(this::uploadFile).toList();
    }

    private void validateFileExists(final MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new RuntimeException();
        }
    }
}