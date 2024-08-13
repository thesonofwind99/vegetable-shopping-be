package com.vegetableshoppingbe.service.impl;

import com.vegetableshoppingbe.converter.UserConverter;
import com.vegetableshoppingbe.dto.request.AccountRequestWithRole;
import com.vegetableshoppingbe.dto.request.UserRequest;
import com.vegetableshoppingbe.dto.response.UserResponse;
import com.vegetableshoppingbe.entity.Role;
import com.vegetableshoppingbe.entity.User;
import com.vegetableshoppingbe.exception.ResourceAlreadyExistsException;
import com.vegetableshoppingbe.exception.ResourceNotFoundException;
import com.vegetableshoppingbe.repository.RoleRepository;
import com.vegetableshoppingbe.mail.MailInfo;
import com.vegetableshoppingbe.repository.UserRepository;
import com.vegetableshoppingbe.security.config.ApplicationSecureConfig;
import com.vegetableshoppingbe.service.UserService;
import com.vegetableshoppingbe.utils.EmailSenderImpl;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ApplicationSecureConfig applicationSecureConfig;
    private final EmailSenderImpl emailSender;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("User", "List", "empty");
        }
        return users.map(UserConverter::toUserResponse);
    }

    @Override
    public UserRequest saveUser(UserRequest userRequest) {

        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new ResourceAlreadyExistsException("User", "username", userRequest.getUsername());
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new ResourceAlreadyExistsException("User", "email", userRequest.getEmail());
        }

        String passEncode = applicationSecureConfig.passwordEncoder().encode(userRequest.getPassword());
        User user = UserConverter.toUser(userRequest);
        user.setPassword(passEncode);

        userRepository.save(user);

        userRequest.setPassword(passEncode);

        return userRequest;
    }

    @Override
    public UserRequest updateUser(Integer userId, UserRequest userRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", "" + userId));

        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new ResourceAlreadyExistsException("User", "username", userRequest.getUsername());
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new ResourceAlreadyExistsException("User", "email", userRequest.getEmail());
        }

        UserConverter.toUser(user, userRequest);
        userRepository.save(user);

        return userRequest;
    }

    @Override
    public UserResponse findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        UserResponse userResponse = UserConverter.toUserResponse(user);
        return userResponse;
    }

    @Override
    public long countUsers() {
        return userRepository.count();
    }

    @Override
    public UserResponse getUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", "" + id));
        return UserConverter.toUserResponse(user);
    }

    @Override
    public AccountRequestWithRole saveUserWithRole(AccountRequestWithRole accountRequest) {

        if (userRepository.existsByUsername(accountRequest.getUserRequest().getUsername())) {
            throw new ResourceAlreadyExistsException("User", "username", accountRequest.getUserRequest().getUsername());
        }

        if (userRepository.existsByEmail(accountRequest.getUserRequest().getEmail())) {
            throw new ResourceAlreadyExistsException("User", "email", accountRequest.getUserRequest().getEmail());
        }
        User user = UserConverter.toUser(accountRequest.getUserRequest());

        Role role = roleRepository.findByName(accountRequest.getRole());

        if (role == null) {
            throw new RuntimeException("Role not found");
        }

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        user.setPassword(encoder.encode(accountRequest.getUserRequest().getPassword()));
        userRepository.save(user);

        return accountRequest;
    }

    @Override
    public String forgotPassword(String email) throws MessagingException {

//        if (!userRepository.existsByEmail(email)) {
//            throw new ResourceNotFoundException("User", "email", email);
//        }
//        User user = userRepository.findByEmail(email);
//        String newPassword = RandomStringUtils.randomAlphanumeric(8);
//        user.setPassword(applicationSecureConfig.passwordEncoder().encode(newPassword));
//        userRepository.save(user);
//        MailInfo mailInfo = new MailInfo();
//        mailInfo.setTo("anhbhps32085@fpt.edu.vn");
//        mailInfo.setSubject("Password Reset");
//        mailInfo.setBody(newPassword);
//        emailSender.sendEmail(mailInfo);
        return null;
    }


}
