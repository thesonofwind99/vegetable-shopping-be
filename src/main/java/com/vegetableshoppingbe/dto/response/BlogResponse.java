package com.vegetableshoppingbe.dto.response;

import com.vegetableshoppingbe.entity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BlogResponse {
    private Integer blogId;
    private String blogTitle;
    private String blogContent;
    private Category blogCategory;
    private String blogCreate_by;
    private String blogImage;
    private boolean blogActive;
    private LocalDateTime blogDate;
}
