package com.vegetableshoppingbe.converter;

import com.vegetableshoppingbe.dto.request.CartItemRequest;
import com.vegetableshoppingbe.dto.response.CartItemResponse;
import com.vegetableshoppingbe.entity.Cart;
import com.vegetableshoppingbe.entity.CartItem;
import com.vegetableshoppingbe.entity.Product;

public class CartItemConverter {

    public static CartItemResponse toCartItemResponse(CartItem cartItem) {
        return CartItemResponse.builder()
                .product(cartItem.getProduct())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getPrice())
                .build();
    }

    public static CartItem toCartItem(CartItemRequest request){
        CartItem cartItem = new CartItem();
        if(request.getProductId() != null){
            Product product = new Product();
            product.setProductId(request.getProductId());
            cartItem.setProduct(product);
        }
        if(request.getQuantity() != null){
            cartItem.setQuantity(request.getQuantity());
        }
        if(request.getPrice() != null){
            cartItem.setPrice(request.getPrice());
        }
        if(request.getCartId() != null){
            Cart cart = new Cart();
            cart.setCartId(request.getCartId());
            cartItem.setCart(cart);
        }
        return cartItem;
    }

}
