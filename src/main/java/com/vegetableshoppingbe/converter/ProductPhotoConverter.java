package com.vegetableshoppingbe.converter;

import com.vegetableshoppingbe.dto.request.ProductPhotoRequest;
import com.vegetableshoppingbe.dto.response.ProductPhotoResponse;
import com.vegetableshoppingbe.entity.Product;
import com.vegetableshoppingbe.entity.ProductPhoto;

public class ProductPhotoConverter {

    public static ProductPhoto toProductPhoto(ProductPhotoRequest request) {
        ProductPhoto productPhoto = new ProductPhoto();
        productPhoto.setPhotoUrl(request.getPhotoUrl());
        Product product = new Product();
        product.setProductId(request.getProductId());
        productPhoto.setProduct(product);
        return productPhoto;
    }

    public static ProductPhotoResponse toProductPhotoResponse(ProductPhoto productPhoto) {
        return ProductPhotoResponse.builder()
                .id(productPhoto.getId())
                .photoUrl(productPhoto.getPhotoUrl())
                .product(productPhoto.getProduct())
                .build();
    }
}
