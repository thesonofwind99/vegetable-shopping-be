package com.vegetableshoppingbe.controller;

import com.vegetableshoppingbe.config.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/checkout/vnpay")
@CrossOrigin(origins = "http://localhost:8081")
public class VNPayController {
    private final VNPayService vnPayService;

    @PostMapping("/submitOrder")
    public String submidOrder(@RequestParam("amount") Integer orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              @RequestParam("baseUrl") String baseUrl){
        System.out.println(baseUrl);
        String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
        return vnpayUrl;
    }
}
