package com.vegetableshoppingbe.controller;

import com.vegetableshoppingbe.dto.request.CategoryRequest;
import com.vegetableshoppingbe.dto.response.CategoryResponse;
import com.vegetableshoppingbe.entity.Category;
import com.vegetableshoppingbe.repository.CategoryRepository;
import com.vegetableshoppingbe.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = {"Authorization"})
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> getCategories(@RequestParam("page") Optional<Integer> page,
                                                                @RequestParam("size") Optional<Integer> size) {
        Integer count = categoryService.countCategory();
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(count));
        return ResponseEntity.ok(categoryService.getCategories(pageable));
    }

    @PostMapping
    public ResponseEntity<CategoryRequest> saveCategory(@Validated @ModelAttribute CategoryRequest categoryRequest,
                                                        @RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.saveCategory(categoryRequest, file));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Category> removeCategory(@PathVariable Integer id) {
        categoryService.removeCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryRequest> updateCategory(@PathVariable Integer id,
                                                          @ModelAttribute CategoryRequest categoryRequest,
                                                          @RequestParam(value = "file", required = false) MultipartFile file) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryRequest, file));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    @GetMapping({"/findCategoryLikeCategoryName"})
    public ResponseEntity<Page<CategoryResponse>> findByCategoryName(@RequestParam(value = "keyword", required = false) String categoryName,
                                                                     @RequestParam("page") Optional<Integer> page,
                                                                     @RequestParam("size") Optional<Integer> size) {
        Integer count = categoryService.countCategory();
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(count));
        return ResponseEntity.ok(categoryService.findCategoriesLikeCategoryName(categoryName,pageable));
    }

}
