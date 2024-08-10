package com.vegetableshoppingbe.service;

import java.io.File;

public interface S3Service {

    String uploadFileToS3(final File file);

    String generatePresignedUrl(String s3Path);
}
