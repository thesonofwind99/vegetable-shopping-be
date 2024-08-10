package com.vegetableshoppingbe.service;

import com.vegetableshoppingbe.dto.request.CartItemRequest;
import com.vegetableshoppingbe.dto.response.CartItemResponse;

import java.util.List;

public interface CartItemService {

    List<CartItemResponse> getCartItemsByCardId(Integer cardId);

    StringBuilder addCartItem(List<CartItemRequest> cartItemRequestList);
}
