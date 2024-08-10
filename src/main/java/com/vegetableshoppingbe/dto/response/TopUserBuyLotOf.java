package com.vegetableshoppingbe.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopUserBuyLotOf {
    private String username;
    private String fullname;
    private String email;
    private String phoneNumber;
    private Double totalAmount;
    private Long orderCount;

    public TopUserBuyLotOf(String username, String fullname, String email, String phoneNumber, Double totalAmount, Long orderCount) {
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.totalAmount = totalAmount;
        this.orderCount = orderCount;
    }
}
