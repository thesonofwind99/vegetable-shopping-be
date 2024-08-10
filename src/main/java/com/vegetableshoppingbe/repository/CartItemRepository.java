package com.vegetableshoppingbe.repository;

import com.vegetableshoppingbe.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findCartItemsByCart_CartId(Integer cartId);

}
