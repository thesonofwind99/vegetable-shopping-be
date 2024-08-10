package com.vegetableshoppingbe.converter;

import com.vegetableshoppingbe.dto.request.CategoryRequest;
import com.vegetableshoppingbe.dto.response.CategoryResponse;
import com.vegetableshoppingbe.entity.Category;

public class CategoryConverter {

    public static CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .categoryImage(category.getCategoryImage())
                .build();
    }

    public static Category toCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setCategoryImage(categoryRequest.getCategoryImage());
        return category;
    }

}
