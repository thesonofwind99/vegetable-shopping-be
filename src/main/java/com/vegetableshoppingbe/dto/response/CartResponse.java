package com.vegetableshoppingbe.dto.response;

import com.vegetableshoppingbe.entity.User;
import com.vegetableshoppingbe.enums.CartStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CartResponse {
    private Integer cartId;
    private String addressShipping;
    private CartStatus cartStatus;
    private String note;
    private boolean paymentMethod;
    private boolean paymentStatus;
    private LocalDateTime createdDate;
    private double shippingFee;
    private double totalAmount;
    private User user;
}
