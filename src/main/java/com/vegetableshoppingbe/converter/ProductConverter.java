package com.vegetableshoppingbe.converter;

import com.vegetableshoppingbe.dto.request.ProductRequest;
import com.vegetableshoppingbe.dto.response.ProductResponse;
import com.vegetableshoppingbe.entity.Category;
import com.vegetableshoppingbe.entity.Product;

public class ProductConverter {

    public static Product toProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setPhoto(productRequest.getPhoto());
        product.setWeight(productRequest.getWeight());

        Category category = new Category();
        category.setCategoryId(productRequest.getCategoryId());
        product.setCategory(category);
        return product;
    }

    public static ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .weight(product.getWeight())
                .photo(product.getPhoto())
                .description(product.getDescription())
                .category(product.getCategory())
                .build();
    }

}
