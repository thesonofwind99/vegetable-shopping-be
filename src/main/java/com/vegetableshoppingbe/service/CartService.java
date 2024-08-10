package com.vegetableshoppingbe.service;

import com.vegetableshoppingbe.dto.request.CartRequest;
import com.vegetableshoppingbe.dto.response.CartResponse;
import com.vegetableshoppingbe.enums.CartStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CartService {

    int countOrderInWeek();

    List<Integer> findYearOrder();

    Page<CartResponse> getCarts(Pageable pageable);

    CartRequest updateCart(Integer id, CartRequest cartRequest);

    CartResponse getCart(Integer id);

    CartResponse addCart(CartRequest cartRequest);

    void deleteCart(Integer id);
    Page<CartResponse> getCartsByCartStatus(CartStatus cartStatus, Pageable pageable);

    Integer countCarts();

    List<CartResponse> getCartsByUserId(Integer userId);

}
