package com.vegetableshoppingbe.dto.response;

import com.vegetableshoppingbe.entity.Product;
import com.vegetableshoppingbe.entity.ProductPhoto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductPhotoResponse {
    private Integer id;
    private String photoUrl;
    private Product product;
}
