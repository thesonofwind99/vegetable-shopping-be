package com.vegetableshoppingbe.converter;

import com.vegetableshoppingbe.dto.request.CartRequest;
import com.vegetableshoppingbe.dto.response.CartResponse;
import com.vegetableshoppingbe.entity.Cart;
import com.vegetableshoppingbe.entity.User;

public class CartConverter {

    public static CartResponse toCartResponse(Cart cart) {
        return CartResponse.builder()
                .cartId(cart.getCartId())
                .addressShipping(cart.getAddressShipping())
                .cartStatus(cart.getCartStatus())
                .note(cart.getNote())
                .paymentMethod(cart.getPaymentMethod())
                .paymentStatus(cart.getPaymentStatus())
                .createdDate(cart.getCreatedDate())
                .shippingFee(cart.getShippingFee())
                .totalAmount(cart.getTotalAmount())
                .user(cart.getUser())
                .build();
    }

    public static void toCart(Cart cart, CartRequest cartRequest) {
        if (cartRequest.getAddressShipping() != null) {
            cart.setAddressShipping(cartRequest.getAddressShipping());
        }
        if (cartRequest.getCartStatus() != null) {
            cart.setCartStatus(cartRequest.getCartStatus());
        }
        if (cartRequest.getNote() != null) {
            cart.setNote(cartRequest.getNote());
        }
        if (cartRequest.getPaymentMethod() != null) {
            cart.setPaymentMethod(cartRequest.getPaymentMethod());
        }
        if (cartRequest.getPaymentStatus() != null) {
            cart.setPaymentStatus(cartRequest.getPaymentStatus());
        }
        if (cartRequest.getShippingFee() != null) {
            cart.setShippingFee(cartRequest.getShippingFee());
        }
        if (cartRequest.getTotalAmount() != null) {
            cart.setTotalAmount(cartRequest.getTotalAmount());
        }
        if (cartRequest.getUserId() != null) {
            User user = new User();
            user.setUserId(cartRequest.getUserId());
            cart.setUser(user);
        }
    }

    public static Cart toCart(CartRequest cartRequest) {
        Cart cart = new Cart();
        cart.setAddressShipping(cartRequest.getAddressShipping());
        cart.setCartStatus(cartRequest.getCartStatus());
        cart.setNote(cartRequest.getNote());
        cart.setPaymentMethod(cartRequest.getPaymentMethod());
        cart.setPaymentStatus(cartRequest.getPaymentStatus());
        cart.setShippingFee(cartRequest.getShippingFee());
        cart.setTotalAmount(cartRequest.getTotalAmount());
        return cart;
    }

}
