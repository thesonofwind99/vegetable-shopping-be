package com.vegetableshoppingbe.dto.response;


import com.vegetableshoppingbe.entity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductResponse {

    private Integer productId;
    private String productName;
    private Integer quantity;
    private Double price;
    private Double weight;
    private String photo;
    private String description;
    private Category category;
}
