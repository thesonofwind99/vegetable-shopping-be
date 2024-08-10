package com.vegetableshoppingbe.service;

import com.vegetableshoppingbe.dto.request.ChangePasswordRequest;
import com.vegetableshoppingbe.dto.request.LoginRequest;

public interface AuthService {
    String login(LoginRequest loginRequest);

    String findRoleNameByUsername(String username);

    void changePassword(Integer id, ChangePasswordRequest request);

}
