package com.vegetableshoppingbe.service.impl;

import com.vegetableshoppingbe.converter.BlogConverter;
import com.vegetableshoppingbe.dto.request.BlogRequest;
import com.vegetableshoppingbe.dto.response.BlogResponse;
import com.vegetableshoppingbe.entity.Blog;
import com.vegetableshoppingbe.entity.Category;
import com.vegetableshoppingbe.exception.ResourceNotFoundException;
import com.vegetableshoppingbe.exception.SystemErrorException;
import com.vegetableshoppingbe.repository.BlogRepository;
import com.vegetableshoppingbe.repository.CategoryRepository;
import com.vegetableshoppingbe.service.BlogService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import com.vegetableshoppingbe.service.ImgBBService;
import com.vegetableshoppingbe.service.S3Service;
import com.vegetableshoppingbe.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final CategoryRepository categoryRepository;

    private final BlogRepository blogRepository;

    private final ImgBBService imgBBService;

    @Override
    public Page<BlogResponse> getAll(Integer pageNo, String keyword, Boolean active, Integer categoryId, Integer blogId, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Blog> blogPage = this.blogRepository.findAllBlogs(active, keyword, categoryId, blogId, pageable);
        return blogPage.map(blog -> {
            BlogResponse blogResponse = BlogConverter.toBlogResponse(blog);
            return blogResponse;
        });
    }


    @Override
    public Page<BlogResponse> getByCategoryId(Integer categoryId, Integer blogId, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 4);
        Page<Blog> blogPage = blogRepository.findByCategoryId(categoryId, blogId, pageable);
        return blogPage.map(blog -> {
            BlogResponse blogResponse = BlogConverter.toBlogResponse(blog);
            return blogResponse;
        });
    }

    @Override
    public BlogRequest saveBlog(BlogRequest blogRequest, MultipartFile file) {
        Category category = categoryRepository.findById(blogRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "categoryId",
                        "" + blogRequest.getCategoryId()));
        String imgBBFilePath;
        try {
            imgBBFilePath = imgBBService.uploadToImgBB(file);
            System.out.println(imgBBFilePath);
//            Files.deleteIfExists(fileConvert.toPath());
        } catch (IOException | SystemErrorException e) {
            throw new SystemErrorException("Error uploading to ImgBB");
        }
        Blog blog = BlogConverter.toBlog(blogRequest);
        blog.setImage(imgBBFilePath);
        blogRepository.save(blog);
        blogRequest.setBlogImage(imgBBFilePath);
        return blogRequest;
    }

    @Override
    public BlogRequest updateBlog(Integer id, BlogRequest blogRequest, MultipartFile file) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", "" + id));
        categoryRepository.findById(blogRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "categoryId",
                        "" + blogRequest.getCategoryId()));
        blogRequest.setBlogImage(blog.getImage());
        blog = BlogConverter.toBlog(blogRequest);
        if (file != null && !file.isEmpty()) {
            try {
               String imgBBFilePath = imgBBService.uploadToImgBB(file);
                System.out.println(imgBBFilePath);
//               Files.deleteIfExists(fileConverter.toPath());
               blog.setImage(imgBBFilePath);
            } catch (IOException | SystemErrorException e) {
                throw new SystemErrorException("Error uploading file to ImgBB");
            }
        }
        blog.setId(id);
        blogRepository.save(blog);
        blogRequest.setBlogImage(blog.getImage());
        return blogRequest;
    }

    @Override
    public BlogResponse getBlog(Integer id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "id", "" + id));
        BlogResponse blogResponse = BlogConverter.toBlogResponse(blog);
        return blogResponse;
    }

    @Override
    public List<BlogResponse> getThreeBlog() {
        List<Blog> blogs = blogRepository.getThreeBlog();
        return blogs.stream().map(blog -> {
            BlogResponse blogResponse = BlogConverter.toBlogResponse(blog);
            return blogResponse;
        }).toList();
    }


}