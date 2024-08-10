package com.vegetableshoppingbe.controller;

import com.vegetableshoppingbe.dto.request.CartItemRequest;
import com.vegetableshoppingbe.dto.response.CartItemResponse;
import com.vegetableshoppingbe.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cartItems")
@CrossOrigin(origins = "http://localhost:8081")
public class CartItemController {

    private final CartItemService cartItemService;

    @GetMapping("/{id}")
    public ResponseEntity<List<CartItemResponse>> getCartItemsByCardId(@PathVariable Integer id) {
        return ResponseEntity.ok().body(cartItemService.getCartItemsByCardId(id));
    }

    @PostMapping
    public ResponseEntity<?> createCartItem(@RequestBody List<CartItemRequest> cartItemRequestList) {
        StringBuilder badResult = cartItemService.addCartItem(cartItemRequestList);
        return ResponseEntity.ok().body(badResult);
    }

}
