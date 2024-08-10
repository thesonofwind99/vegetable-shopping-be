package com.vegetableshoppingbe.controller;

import com.vegetableshoppingbe.dto.request.ProductPhotoRequest;
import com.vegetableshoppingbe.dto.response.ProductPhotoResponse;
import com.vegetableshoppingbe.service.ProductPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product-photos")
@CrossOrigin(origins = "http://localhost:8081")
public class ProductPhotoController {

    private final ProductPhotoService productPhotoService;

    @PostMapping
    public ResponseEntity<ProductPhotoRequest> saveProductPhoto(@ModelAttribute ProductPhotoRequest productPhotoRequest,
                                                                @RequestParam(value = "file", required = false) MultipartFile file) {
        return ResponseEntity.ok(productPhotoService.saveProductPhoto(productPhotoRequest, file));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ProductPhotoResponse>> getProductPhotoByProductId(@PathVariable Integer id) {
        List<ProductPhotoResponse> list = productPhotoService.getProductPhotosByProductId(id);
        return ResponseEntity.ok(list);
    }
}
