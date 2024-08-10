package com.vegetableshoppingbe.service.impl;

import com.vegetableshoppingbe.converter.CartConverter;
import com.vegetableshoppingbe.dto.request.CartRequest;
import com.vegetableshoppingbe.dto.response.CartResponse;
import com.vegetableshoppingbe.entity.Cart;
import com.vegetableshoppingbe.enums.CartStatus;
import com.vegetableshoppingbe.entity.CartItem;
import com.vegetableshoppingbe.entity.User;
import com.vegetableshoppingbe.exception.ResourceNotFoundException;
import com.vegetableshoppingbe.repository.CartItemRepository;
import com.vegetableshoppingbe.repository.CartRepository;
import com.vegetableshoppingbe.repository.UserRepository;
import com.vegetableshoppingbe.service.CartService;
import com.vegetableshoppingbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final ModelMapper modelMapper;

    @Override
    public int countOrderInWeek() {
        return cartRepository.countOrderInWeek();
    }

    @Override
    public List<Integer> findYearOrder() {
        return cartRepository.findYearOrder();
    }

    @Override
    public Page<CartResponse> getCarts(Pageable pageable) {
        Page<Cart> carts = cartRepository.findAll(pageable);
        return carts.map(CartConverter::toCartResponse);
    }

    @Override
    public CartRequest updateCart(Integer id, CartRequest cartRequest) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", "" + id));
        CartConverter.toCart(cart, cartRequest);
        cartRepository.save(cart);
        modelMapper.map(cart, cartRequest);
        return cartRequest;
    }

    @Override
    public CartResponse getCart(Integer id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", "" + id));
        return CartConverter.toCartResponse(cart);
    }

    @Override
    public Page<CartResponse> getCartsByCartStatus(CartStatus cartStatus, Pageable pageable) {
        return cartRepository.getCartsByCartStatus(cartStatus, pageable)
                .map(CartConverter::toCartResponse);
    }

    @Override
    public Integer countCarts() {
        return Math.toIntExact(cartRepository.count());
    }
    @Override
    public CartResponse addCart(CartRequest cartRequest) {
        Cart cart = new Cart();
        CartConverter.toCart(cart, cartRequest);
        cartRepository.save(cart);
        return CartConverter.toCartResponse(cart);
    }

    @Override
    public void deleteCart(Integer id) {
        List<CartItem> list = cartRepository.findCartItemsByCartId(id);
        for (CartItem cartItem : list) {
            cartItemRepository.deleteById(cartItem.getCartItemId());
        }
        cartRepository.deleteById(id);
    }

    @Override
    public List<CartResponse> getCartsByUserId(Integer userId) {
        return cartRepository.getCartsByUser_UserId(userId)
                .stream()
                .map(CartConverter::toCartResponse)
                .toList();
    }

}
