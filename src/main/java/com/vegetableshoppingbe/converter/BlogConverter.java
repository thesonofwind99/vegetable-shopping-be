package com.vegetableshoppingbe.converter;

import com.vegetableshoppingbe.dto.request.BlogRequest;
import com.vegetableshoppingbe.dto.response.BlogResponse;
import com.vegetableshoppingbe.entity.Blog;
import com.vegetableshoppingbe.entity.Category;

public class BlogConverter {

    public static Blog toBlog(BlogRequest blogRequest){
        Blog blog = new Blog();
        blog.setTitle(blogRequest.getBlogTitle());
        blog.setContent(blogRequest.getBlogContent());
        blog.setActive(blogRequest.isBlogActive());
        blog.setCreate_by(blogRequest.getCreate_by());
        blog.setImage(blogRequest.getBlogImage());
        Category category = new Category();
        category.setCategoryId(blogRequest.getCategoryId());
        blog.setCategory(category);
        return blog;
    }

    public static BlogResponse toBlogResponse(Blog blog){
        return BlogResponse.builder()
                .blogId(blog.getId())
                .blogTitle(blog.getTitle())
                .blogContent(blog.getContent())
                .blogCategory(blog.getCategory())
                .blogCreate_by(blog.getCreate_by())
                .blogImage(blog.getImage())
                .blogActive(blog.isActive())
                .blogDate(blog.getCreatedDate())
                .build();
    }
}
