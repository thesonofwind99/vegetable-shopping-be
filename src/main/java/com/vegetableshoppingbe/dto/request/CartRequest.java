package com.vegetableshoppingbe.dto.request;

import com.vegetableshoppingbe.enums.CartStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CartRequest {
    private String addressShipping;
    private CartStatus cartStatus;
    private String note;
    private Boolean paymentMethod;
    private Boolean paymentStatus;
    private LocalDateTime createdDate;
    private Double shippingFee;
    private Double totalAmount;
    private Integer userId;
}
