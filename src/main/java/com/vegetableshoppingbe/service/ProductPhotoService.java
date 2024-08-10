package com.vegetableshoppingbe.service;

import com.vegetableshoppingbe.dto.request.ProductPhotoRequest;
import com.vegetableshoppingbe.dto.response.ProductPhotoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductPhotoService {

    ProductPhotoRequest saveProductPhoto(ProductPhotoRequest productRequest, MultipartFile file);

    List<ProductPhotoResponse> getProductPhotosByProductId(Integer productId);
}
