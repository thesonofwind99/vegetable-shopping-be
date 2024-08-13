package com.vegetableshoppingbe.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImgBBService {
    String uploadToImgBB(MultipartFile file) throws IOException;
}
