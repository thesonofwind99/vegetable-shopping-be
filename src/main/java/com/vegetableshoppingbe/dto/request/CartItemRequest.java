package com.vegetableshoppingbe.dto.request;

import com.vegetableshoppingbe.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequest {
    private Integer quantity;
    private Double price;
    private Integer productId;
    private Integer cartId;
}
