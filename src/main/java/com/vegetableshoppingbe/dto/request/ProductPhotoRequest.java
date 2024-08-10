package com.vegetableshoppingbe.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPhotoRequest {
    private Integer productId;
    private String photoUrl;
}
