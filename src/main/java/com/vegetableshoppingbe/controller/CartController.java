package com.vegetableshoppingbe.controller;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.vegetableshoppingbe.dto.request.CartRequest;
import com.vegetableshoppingbe.dto.response.CartResponse;
import com.vegetableshoppingbe.dto.response.CategoryResponse;
import com.vegetableshoppingbe.enums.CartStatus;
import com.vegetableshoppingbe.repository.CartRepository;
import com.vegetableshoppingbe.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
@CrossOrigin(origins = "http://localhost:8081")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<Page<CartResponse>> getCarts(@RequestParam("page") Optional<Integer> page,
                                                       @RequestParam("size") Optional<Integer> size) {
        Integer count = cartService.countCarts();
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(count));
        return ResponseEntity.ok().body(cartService.getCarts(pageable));
    }

    @GetMapping("/countOrderInWeek")
    public ResponseEntity<Integer> count() {
        return ResponseEntity.ok(cartService.countOrderInWeek());
    }

    @GetMapping("/findYearOrder")
    public ResponseEntity<List<Integer>> findYearOrder() {
        return ResponseEntity.ok(cartService.findYearOrder());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartRequest> updateCart(@PathVariable Integer id,
                                                  @RequestBody CartRequest cartRequest) {
        return ResponseEntity.ok(cartService.updateCart(id, cartRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getCart(@PathVariable Integer id) {
        return ResponseEntity.ok(cartService.getCart(id));
    }

    @GetMapping("/getCartsByCartStatus")
    public ResponseEntity<Page<CartResponse>> getCartsByCartStatus(@RequestParam("cartStatus") CartStatus cartStatus,
                                                                   @RequestParam("page") Optional<Integer> page,
                                                                   @RequestParam("size") Optional<Integer> size) {
        Integer count = cartService.countCarts();
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(count));
        return ResponseEntity.ok(cartService.getCartsByCartStatus(cartStatus, pageable));
    }
    @PostMapping
    public ResponseEntity<CartResponse> createCart(@RequestBody CartRequest cartRequest) {
        CartResponse cart = cartService.addCart(cartRequest);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable Integer id) {
        cartService.deleteCart(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getCartsByUserId/{id}")
    public ResponseEntity<List<CartResponse>> getCartsByUserId(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCartsByUserId(id));
    }

}
