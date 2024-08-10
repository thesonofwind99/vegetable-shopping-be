package com.vegetableshoppingbe.service;

import com.vegetableshoppingbe.dto.request.BlogRequest;
import com.vegetableshoppingbe.dto.response.BlogResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface BlogService {

    BlogRequest saveBlog(BlogRequest blogRequest, MultipartFile file);

    BlogRequest updateBlog(Integer id, BlogRequest blogRequest, MultipartFile file);

    BlogResponse getBlog(Integer id);

    Page<BlogResponse> getAll(Integer pageNo, String keyword, Boolean active, Integer categoryId, Integer blogId,  Integer pageSize);

    Page<BlogResponse> getByCategoryId(Integer categoryId, Integer blogId, Integer pageNo);

    List<BlogResponse> getThreeBlog ();

}