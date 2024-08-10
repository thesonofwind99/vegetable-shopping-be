package com.vegetableshoppingbe.service;

import com.vegetableshoppingbe.dto.request.AccountRequestWithRole;
import com.vegetableshoppingbe.dto.request.UserRequest;
import com.vegetableshoppingbe.dto.response.UserResponse;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserResponse> getAllUsers(Pageable pageable);

    UserRequest saveUser(UserRequest userRequest);

    UserRequest updateUser(Integer id, UserRequest userRequest);

    long countUsers();

    UserResponse getUser(Integer id);

    UserResponse findByUsername(String username);

    AccountRequestWithRole saveUserWithRole(AccountRequestWithRole accountRequest);

    String forgotPassword(String email) throws MessagingException; ;
}
