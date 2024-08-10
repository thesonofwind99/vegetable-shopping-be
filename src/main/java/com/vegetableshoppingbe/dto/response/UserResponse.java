package com.vegetableshoppingbe.dto.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
public class UserResponse {
    private Integer userId;
    private String username;
    private String fullname;
    private String email;
    private String phoneNumber;
    private Date dayOfBirth;
    private String address;
    private String gender;
    private Boolean active;


}
