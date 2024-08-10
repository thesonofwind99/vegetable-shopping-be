package com.vegetableshoppingbe.service;

import com.vegetableshoppingbe.dto.request.ProductRequest;
import com.vegetableshoppingbe.dto.response.ProductResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    Page<ProductResponse> getProducts(Pageable pageable);

    List<ProductResponse> getFeatureProducts();

    Integer saveProduct(ProductRequest productRequest, MultipartFile file);

    Page<ProductResponse> getAllProductsByCategory(Integer category, int page, int size,
            String sort);

    void removeProduct(Integer id);

    ProductRequest updateProduct(Integer id, ProductRequest productRequest, MultipartFile file);

    ProductResponse getProduct(Integer id);

    Long countProducts();

    Page<ProductResponse> findProductsLikeProductName(String productName, Pageable pageable);

    void updateQuantityProduct(Integer id, Integer quantity);
}
