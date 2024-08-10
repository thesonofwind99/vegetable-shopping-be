package com.vegetableshoppingbe.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class AccountRequestWithRole {
    private UserRequest userRequest;
    private String role;

}
