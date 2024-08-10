package com.vegetableshoppingbe.service.impl;

import com.vegetableshoppingbe.dto.request.ChangePasswordRequest;
import com.vegetableshoppingbe.dto.request.LoginRequest;
import com.vegetableshoppingbe.entity.User;
import com.vegetableshoppingbe.exception.ResourceAlreadyExistsException;
import com.vegetableshoppingbe.repository.UserRepository;
import com.vegetableshoppingbe.security.jwt.JwtTokenProvider;
import com.vegetableshoppingbe.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public String login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public String findRoleNameByUsername(String username) {
        return userRepository.findRoleNameByUsername(username);
    }

    @Override
    public void changePassword(Integer id, ChangePasswordRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceAlreadyExistsException("User", "username","" + id));

        if (!encoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect old password");
        }

        user.setPassword(encoder.encode(request.getNewPassword()));
        request.setNewPassword(encoder.encode(request.getNewPassword()));
        userRepository.save(user);

    }
}
