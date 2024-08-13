package com.vegetableshoppingbe.service.impl;

import com.vegetableshoppingbe.converter.CategoryConverter;
import com.vegetableshoppingbe.dto.request.CategoryRequest;
import com.vegetableshoppingbe.dto.response.CategoryResponse;
import com.vegetableshoppingbe.entity.Category;
import com.vegetableshoppingbe.exception.ResourceNotFoundException;
import com.vegetableshoppingbe.exception.SystemErrorException;
import com.vegetableshoppingbe.repository.CategoryRepository;
import com.vegetableshoppingbe.service.CategoryService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import com.vegetableshoppingbe.service.ImgBBService;
import com.vegetableshoppingbe.service.S3Service;
import com.vegetableshoppingbe.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ImgBBService imgBBService;

    @Override
    public Page<CategoryResponse> getCategories(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);

        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("Category", "list", "is empty");
        }

        return categories.map(category -> {
            CategoryResponse categoryResponse = CategoryConverter.toCategoryResponse(category);
            categoryResponse.setCategoryImage(categoryResponse.getCategoryImage());
            return categoryResponse;
        });
    }

    @Override
    public CategoryRequest saveCategory(CategoryRequest categoryRequest, MultipartFile file) {
        //Upload image to ImgBB
        String imgBBFilePath;
        try {
            imgBBFilePath = imgBBService.uploadToImgBB(file);
            System.out.println(imgBBFilePath);
//            Files.deleteIfExists(fileConvert.toPath());
        } catch (SystemErrorException e) {
            throw new SystemErrorException("Error uploading file to ImgBB");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Category category = CategoryConverter.toCategory(categoryRequest);
        category.setCategoryImage(imgBBFilePath);
        categoryRepository.save(category);
        categoryRequest.setCategoryImage(imgBBFilePath);

        return categoryRequest;
    }

    @Override
    public void removeCategory(Integer id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Category", "id", "" + id);
        }
    }

    @Override
    public CategoryRequest updateCategory(Integer id,
                                          CategoryRequest categoryRequest,
                                          MultipartFile file) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", "" + id));
        String imgBBFilePath;
        if (file != null && !file.isEmpty()) {
            try {
                imgBBFilePath = imgBBService.uploadToImgBB(file);
                System.out.println(imgBBFilePath);
//            Files.deleteIfExists(fileConvert.toPath());
                category.setCategoryImage(imgBBFilePath);
            } catch (SystemErrorException | IOException e) {
                throw new SystemErrorException("Error uploading to ImgBB");
            }
        }

        if (categoryRequest.getCategoryName() != null) {
            category.setCategoryName(categoryRequest.getCategoryName());
        }

        categoryRepository.save(category);
        categoryRequest.setCategoryImage(category.getCategoryImage());

        return categoryRequest;
    }

    @Override
    public CategoryResponse getCategory(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", "" + id));
        return CategoryConverter.toCategoryResponse(category);
    }

    @Override
    public Page<CategoryResponse> findCategoriesLikeCategoryName(String categoryName, Pageable pageable) {
        return categoryRepository.findCategoriesLikeCategoryName(categoryName, pageable)
                .map(CategoryConverter::toCategoryResponse);
    }

    @Override
    public Integer countCategory() {
        return Math.toIntExact(categoryRepository.count());
    }

}