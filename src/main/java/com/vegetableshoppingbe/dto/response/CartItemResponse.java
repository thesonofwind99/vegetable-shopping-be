package com.vegetableshoppingbe.dto.response;

import com.vegetableshoppingbe.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CartItemResponse {
    private Product product;
    private Integer quantity;
    private Double price;
}
