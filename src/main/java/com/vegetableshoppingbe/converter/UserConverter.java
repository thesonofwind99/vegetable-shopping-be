package com.vegetableshoppingbe.converter;

import com.vegetableshoppingbe.dto.request.UserRequest;
import com.vegetableshoppingbe.dto.response.UserResponse;
import com.vegetableshoppingbe.entity.User;

public class UserConverter {

    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .fullname(user.getFullname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .dayOfBirth(user.getDayOfBirth())
                .address(user.getAddress())
                .gender(user.getGender())
                .active(user.getActive())
                .build();
    }

    public static void toUser(User user, UserRequest userRequest) {
        if (userRequest.getUsername() != null) {
            user.setUsername(userRequest.getUsername());
        }
        if (userRequest.getFullname() != null) {
            user.setFullname(userRequest.getFullname());
        }
        if (userRequest.getEmail() != null) {
            user.setEmail(userRequest.getEmail());
        }
        if (userRequest.getPhoneNumber() != null) {
            user.setPhoneNumber(userRequest.getPhoneNumber());
        }
        if (userRequest.getDayOfBirth() != null) {
            user.setDayOfBirth(userRequest.getDayOfBirth());
        }
        if (userRequest.getAddress() != null) {
            user.setAddress(userRequest.getAddress());
        }
        if (userRequest.getGender() != null) {
            user.setGender(userRequest.getGender());
        }
        if (userRequest.getActive() != null) {
            user.setActive(userRequest.getActive());
        }
    }

    public static User toUser(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setFullname(userRequest.getFullname());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setDayOfBirth(userRequest.getDayOfBirth());
        user.setAddress(userRequest.getAddress());
        user.setGender(userRequest.getGender());
        user.setActive(userRequest.getActive());
        return user;
    }
}
