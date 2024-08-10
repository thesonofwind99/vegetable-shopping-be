package com.vegetableshoppingbe.service;

import com.vegetableshoppingbe.dto.request.CategoryRequest;
import com.vegetableshoppingbe.dto.response.CategoryResponse;
import com.vegetableshoppingbe.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

    Page<CategoryResponse> getCategories(Pageable pageable);

    CategoryRequest saveCategory(CategoryRequest categoryRequest, MultipartFile file);

    void removeCategory(Integer id);

    CategoryRequest updateCategory(Integer id, CategoryRequest categoryRequest, MultipartFile file);

    CategoryResponse getCategory(Integer category);

    Page<CategoryResponse> findCategoriesLikeCategoryName(String categoryName, Pageable pageable);

    Integer countCategory();
}
