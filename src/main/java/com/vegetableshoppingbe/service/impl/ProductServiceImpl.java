package com.vegetableshoppingbe.service.impl;

import com.vegetableshoppingbe.converter.ProductConverter;
import com.vegetableshoppingbe.dto.projection.FeatureProduct;
import com.vegetableshoppingbe.dto.request.ProductRequest;
import com.vegetableshoppingbe.dto.response.ProductResponse;
import com.vegetableshoppingbe.entity.Category;
import com.vegetableshoppingbe.entity.Product;
import com.vegetableshoppingbe.exception.ResourceNotFoundException;
import com.vegetableshoppingbe.exception.SystemErrorException;
import com.vegetableshoppingbe.repository.CategoryRepository;
import com.vegetableshoppingbe.repository.ProductRepository;
import com.vegetableshoppingbe.service.ProductService;
import com.vegetableshoppingbe.service.S3Service;
import com.vegetableshoppingbe.utils.FileUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final S3Service s3Service;

    private final ModelMapper modelMapper;


    /**
     * ProductRequest nhận tham số từ client ProductResponse truyền tham số từ server lên cho client
     * ModelMapper để ánh xạ từ entiy sang request hoặc response và ngược lại
     */
    @Override
    public Page<ProductResponse> getProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Product", "list", "empty");
        }

        return products.map(product -> {
            ProductResponse productResponse = ProductConverter.toProductResponse(product);
            productResponse.setPhoto(s3Service.generatePresignedUrl(productResponse.getPhoto()));
            return productResponse;
        });
    }

    @Override
    public List<ProductResponse> getFeatureProducts() {
        List<FeatureProduct> featureProducts = productRepository.getFeatureProducts();
        return featureProducts.stream()
                .map(featureProduct -> ProductResponse.builder()
                        .productId(featureProduct.getProductId())
                        .productName(featureProduct.getProductName())
                        .photo(s3Service.generatePresignedUrl(featureProduct.getPhoto()))
                        .price(featureProduct.getPrice())
                        .build()).toList();
    }

    @Override
    public Integer saveProduct(ProductRequest productRequest, MultipartFile file) {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "categoryId",
                        "" + productRequest.getCategoryId()));

        // upload image to S3
        String s3FilePath;
        try {
            File fileConvert = FileUtil.convertMultiPartToFile(file);
            s3FilePath = s3Service.uploadFileToS3(fileConvert);
            Files.deleteIfExists(fileConvert.toPath());
        } catch (SystemErrorException | IOException e) {
            throw new SystemErrorException("Error uploading file to S3");
        }

        Product product = ProductConverter.toProduct(productRequest);
        product.setPhoto(s3FilePath);
        productRepository.save(product);

        return product.getProductId();
    }

    @Override
    public Page<ProductResponse> getAllProductsByCategory(Integer category, int page, int size,
            String sort) {

        Sort sorting = sort.equalsIgnoreCase("asc")
                       ? Sort.by(Sort.Direction.ASC, "price")
                       : Sort.by(Sort.Direction.DESC, "price");
        Pageable pageable = PageRequest.of(page, size, sorting);
        Page<Product> products = productRepository.findProductByCategoryId(category, pageable);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Product", "list", "empty");
        }
        return products.map(product -> {
            ProductResponse productResponse = ProductConverter.toProductResponse(product);
            productResponse.setPhoto(s3Service.generatePresignedUrl(productResponse.getPhoto()));
            return productResponse;
        });
    }


    @Override
    public void removeProduct(Integer id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Product ", "id", "" + id);
        }
    }

    @Override
    public ProductRequest updateProduct(Integer id,
            ProductRequest productRequest,
            MultipartFile file) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", "" + id));

        categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "categoryId",
                        "" + productRequest.getCategoryId()));

        productRequest.setPhoto(product.getPhoto());
        product = ProductConverter.toProduct(productRequest);

        if (file != null && !file.isEmpty()) {
            try {
                File fileConverted = FileUtil.convertMultiPartToFile(file);
                String s3FilePath = s3Service.uploadFileToS3(fileConverted);
                Files.deleteIfExists(fileConverted.toPath());
                product.setPhoto(s3FilePath);
            } catch (IOException | SystemErrorException e) {
                throw new SystemErrorException("Error uploading file to S3");
            }
        }

        product.setProductId(id);
        productRepository.save(product);
        productRequest.setPhoto(product.getPhoto());

        return productRequest;
    }

    @Override
    public ProductResponse getProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", "" + id));
        ProductResponse productResponse = ProductConverter.toProductResponse(product);
        productResponse.setPhoto(s3Service.generatePresignedUrl(product.getPhoto()));
        return productResponse;
    }

    @Override
    public Long countProducts() {
        return productRepository.count();
    }

    @Override
    public Page<ProductResponse> findProductsLikeProductName(String productName, Pageable pageable) {
        return productRepository.findProductsLikeProductName(productName, pageable)
                .map(ProductConverter::toProductResponse);
    }

    @Override
    public void updateQuantityProduct(Integer id, Integer quantity) {
        Product product = productRepository.findById(id).get();
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }

}