package com.vegetableshoppingbe.controller;

import com.vegetableshoppingbe.dto.request.ProductRequest;
import com.vegetableshoppingbe.dto.response.ProductResponse;
import com.vegetableshoppingbe.entity.Product;
import com.vegetableshoppingbe.repository.ProductRepository;
import com.vegetableshoppingbe.service.ProductService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "http://localhost:8081")
public class ProductController {

    private final ProductService productServiceImpl;
    private final ProductRepository productRepository;

    @GetMapping()
    public ResponseEntity<Page<ProductResponse>> getProducts(@RequestParam("page") Optional<Integer> page,
                                                             @RequestParam("size") Optional<Integer> size) {
        Integer count = Math.toIntExact(productServiceImpl.countProducts());
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(count));
        Page<ProductResponse> productResponses = productServiceImpl.getProducts(pageable);
        return ResponseEntity.ok(productResponses);
    }

    @GetMapping("/feature-product")
    public ResponseEntity<List<ProductResponse>> getFeatureProducts() {
        return ResponseEntity.ok(productServiceImpl.getFeatureProducts());
    }

    @GetMapping("/category")
    public ResponseEntity<Page<ProductResponse>> getProductByCategory(
            @RequestParam(value = "id", required = false) Integer categoryId,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort", defaultValue = "asc") String sort) {

        Page<ProductResponse> productResponses = productServiceImpl.getAllProductsByCategory(
                categoryId, page, size, sort);
        return ResponseEntity.ok(productResponses);

    }

    @PostMapping()
    public ResponseEntity<Integer> saveProduct(@ModelAttribute ProductRequest productRequest,
            @RequestParam MultipartFile file) {
        return ResponseEntity.ok(productServiceImpl.saveProduct(productRequest, file));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> removeProduct(@PathVariable Integer id) {
        productServiceImpl.removeProduct(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductRequest> updateProduct(@PathVariable Integer id,
            @ModelAttribute ProductRequest productRequest,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        return ResponseEntity.ok(productServiceImpl.updateProduct(id, productRequest, file));
    }

    @PutMapping("/quantity/{id}/{qty}")
    public ResponseEntity<Void> updateQuantity(@PathVariable Integer id, @PathVariable Integer qty) {
        productServiceImpl.updateQuantityProduct(id, qty);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Integer id) {
        return ResponseEntity.ok(productServiceImpl.getProduct(id));
    }

    @GetMapping("/countProducts")
    public ResponseEntity<Long> countProducts() {
        return ResponseEntity.ok(productServiceImpl.countProducts());
    }

    @GetMapping("/findProductsLikeProductName")
    public ResponseEntity<Page<ProductResponse>> findProductsLikeProductName(@RequestParam(value = "productName", required = false) String productName,
                                                                             @RequestParam("page") Optional<Integer> page,
                                                                             @RequestParam("size") Optional<Integer> size) {
        Integer count = Math.toIntExact(productServiceImpl.countProducts());
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(count));
        return ResponseEntity.ok(productServiceImpl.findProductsLikeProductName(productName, pageable));
    }


}
