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
    private final S3Service s3Service;

    @Override
    public Page<CategoryResponse> getCategories(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);

        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("Category", "list", "is empty");
        }

        return categories.map(category -> {
            CategoryResponse categoryResponse = CategoryConverter.toCategoryResponse(category);
            categoryResponse.setCategoryImage(s3Service.generatePresignedUrl(categoryResponse.getCategoryImage()));
            return categoryResponse;
        });
    }

    @Override
    public CategoryRequest saveCategory(CategoryRequest categoryRequest, MultipartFile file) {
        //Upload image to S3
        String s3FilePath;
        try {
            File fileConvert = FileUtil.convertMultiPartToFile(file);
            s3FilePath = s3Service.uploadFileToS3(fileConvert);
            Files.deleteIfExists(fileConvert.toPath());
        } catch (SystemErrorException | IOException e) {
            throw new SystemErrorException("Error uploading file to S3");
        }

        Category category = CategoryConverter.toCategory(categoryRequest);
        category.setCategoryImage(s3FilePath);
        categoryRepository.save(category);
        categoryRequest.setCategoryImage(s3FilePath);

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

        if (file != null && !file.isEmpty()) {
            try {
                File fileConverter = FileUtil.convertMultiPartToFile(file);
                String s3FilePath = s3Service.uploadFileToS3(fileConverter);
                Files.deleteIfExists(fileConverter.toPath());
                category.setCategoryImage(s3FilePath);
            } catch (SystemErrorException | IOException e) {
                throw new SystemErrorException("Error uploading to S3");
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