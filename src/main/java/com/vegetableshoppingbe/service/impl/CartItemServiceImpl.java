package com.vegetableshoppingbe.service.impl;

import com.vegetableshoppingbe.converter.CartItemConverter;
import com.vegetableshoppingbe.dto.request.CartItemRequest;
import com.vegetableshoppingbe.dto.response.CartItemResponse;
import com.vegetableshoppingbe.entity.CartItem;
import com.vegetableshoppingbe.entity.Product;
import com.vegetableshoppingbe.repository.CartItemRepository;
import com.vegetableshoppingbe.repository.ProductRepository;
import com.vegetableshoppingbe.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    private final ProductRepository productRepository;

    @Override
    public List<CartItemResponse> getCartItemsByCardId(Integer cardId) {
        List<CartItem> cartItems = cartItemRepository.findCartItemsByCart_CartId(cardId);
        return cartItems.stream()
                .map(CartItemConverter::toCartItemResponse)
                .toList();
    }

    @Override
    public StringBuilder addCartItem(List<CartItemRequest> cartItemRequestList) {
        StringBuilder badResult = new StringBuilder();
        for (CartItemRequest cartItemRequest : cartItemRequestList) {
            Product product = productRepository.findById(cartItemRequest.getProductId()).get();
            if(cartItemRequest.getQuantity() > product.getQuantity()){
                badResult.append("The quantity of " + product.getProductName() + " is greater than the inventory quantity (" + product.getQuantity() +")" + ".\n");
            }
        }
        if(badResult.isEmpty()){
            for (CartItemRequest cartItemRequest : cartItemRequestList) {
                cartItemRepository.save(CartItemConverter.toCartItem(cartItemRequest));
            }
        }

        return badResult;
    }

}
