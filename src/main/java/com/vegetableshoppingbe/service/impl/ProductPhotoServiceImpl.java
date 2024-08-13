package com.vegetableshoppingbe.service.impl;

//import com.google.api.services.drive.Drive;

import com.vegetableshoppingbe.converter.ProductPhotoConverter;
import com.vegetableshoppingbe.dto.request.ProductPhotoRequest;
import com.vegetableshoppingbe.dto.response.ProductPhotoResponse;
import com.vegetableshoppingbe.entity.Product;
import com.vegetableshoppingbe.entity.ProductPhoto;
import com.vegetableshoppingbe.exception.ResourceNotFoundException;
import com.vegetableshoppingbe.exception.SystemErrorException;
import com.vegetableshoppingbe.repository.ProductPhotoRepository;
import com.vegetableshoppingbe.repository.ProductRepository;
import com.vegetableshoppingbe.service.ImgBBService;
import com.vegetableshoppingbe.service.ProductPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductPhotoServiceImpl implements ProductPhotoService {

    private final ProductPhotoRepository productPhotoRepository;

    private final ProductRepository productRepository;
    private final ImgBBService imgBBService;

//    private final Drive drive;

    @Override
    public ProductPhotoRequest saveProductPhoto(ProductPhotoRequest request,
            MultipartFile file) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("ProductPhoto", "ProductId",
                        "" + request.getProductId()));
        //Upload image to ImgBB
        String imgBBFilePath;
        try {
            imgBBFilePath = imgBBService.uploadToImgBB(file);
            System.out.println(imgBBFilePath);
//            Files.deleteIfExists(fileConvert.toPath());
        } catch (IOException | SystemErrorException e) {
            throw new SystemErrorException("Error uploading to ImgBB");
        }
        ProductPhoto productPhoto = ProductPhotoConverter.toProductPhoto(request);
        productPhoto.setPhotoUrl(imgBBFilePath);
        productPhotoRepository.save(productPhoto);
        request.setPhotoUrl(productPhoto.getPhotoUrl());
        return request;
    }

    @Override
    public List<ProductPhotoResponse> getProductPhotosByProductId(Integer productId) {
        List<ProductPhoto> productPhotos = productPhotoRepository.findByProductId(productId);
        List<ProductPhotoResponse> productPhotoResponses = new ArrayList<>();
        for (ProductPhoto productPhoto : productPhotos) {
            ProductPhotoResponse productPhotoResponse = ProductPhotoConverter.toProductPhotoResponse(productPhoto);
            productPhotoResponses.add(productPhotoResponse);
        }
        return productPhotoResponses;
    }
}
