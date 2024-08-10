package com.vegetableshoppingbe.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequest{
    private String productName;
    private Integer quantity;
    private Double price;
    private Double weight;
    private String photo;
    private String description;
    private Integer categoryId;
}