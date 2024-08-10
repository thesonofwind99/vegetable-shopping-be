package com.vegetableshoppingbe.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.vegetableshoppingbe.exception.SystemErrorException;
import com.vegetableshoppingbe.service.S3Service;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket.name}")
    private String bucketName;

    @Override
    public String uploadFileToS3(File file) {
        try {
            return uploadFileToS3Bucket(bucketName, file);
        } catch (AmazonServiceException | IOException e) {
            log.error(e.getMessage());
            throw new SystemErrorException("Error uploading file to S3");
        }
    }

    @Override
    public String generatePresignedUrl(String s3Path) {

        // Set expiration time
        Date expiration = Date.from(Instant.now().plus(Duration.ofDays(1)));

        // Generate the presigned URL request
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, s3Path)
                        .withMethod(com.amazonaws.HttpMethod.GET)
                        .withExpiration(expiration)
                        // Optionally add response headers (like setting content disposition, content type etc.)
                        .withResponseHeaders(new ResponseHeaderOverrides().withContentType(
                                "application/octet-stream"));

        // Generate the presigned URL
        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toExternalForm();
    }


    private String uploadFileToS3Bucket(final String bucketName, final File file)
            throws IOException {
        String fileName = Instant.now().toEpochMilli() + "_" + file.getName().replace(" ", "_");

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);

        s3Client.putObject(putObjectRequest);
        return fileName;
    }
}
