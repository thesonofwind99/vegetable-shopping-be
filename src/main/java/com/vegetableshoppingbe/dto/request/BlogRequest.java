package com.vegetableshoppingbe.dto.request;

import com.vegetableshoppingbe.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogRequest {
    private String blogTitle;
    private String blogContent;
    private String create_by;
    private String blogImage;
    private Integer categoryId;
    private boolean blogActive;
}
