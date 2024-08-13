package com.vegetableshoppingbe.controller;

import com.vegetableshoppingbe.dto.request.AccountRequestWithRole;
import com.vegetableshoppingbe.dto.request.UserRequest;
import com.vegetableshoppingbe.dto.response.UserResponse;
import com.vegetableshoppingbe.entity.User;
import com.vegetableshoppingbe.service.impl.UserServiceImpl;
import com.vegetableshoppingbe.service.UserService;
import java.util.List;
import java.util.Optional;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.InternationalFormatter;
import javax.swing.text.html.Option;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:8081")
public class UserController {

    private final UserService userServiceImpl;

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(@RequestParam("page")Optional<Integer> page,
                                                          @RequestParam("size")Optional<Integer> size) {
        Integer count = Math.toIntExact(userServiceImpl.countUsers());
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(count));
        Page<UserResponse> usersResponse = userServiceImpl.getAllUsers(pageable);
        return ResponseEntity.ok(usersResponse);
    }

    @PostMapping
    public ResponseEntity<UserRequest> addUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userServiceImpl.saveUser(userRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRequest> updateUser(@PathVariable Integer id,
                                                   @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userServiceImpl.updateUser(id, userRequest));
    }

    @GetMapping("/user-id/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id) {
        UserResponse userResponse = userServiceImpl.getUser(id);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String username) {
        UserResponse userResponse = userServiceImpl.findByUsername(username);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/countUsers")
    public ResponseEntity<Long> countUsers() {
        long countUsers = userServiceImpl.countUsers();
        return ResponseEntity.ok(countUsers);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) throws MessagingException {
        return ResponseEntity.ok().body(userServiceImpl.forgotPassword(email));
    }


    @PostMapping("/saveWithRole")
    public ResponseEntity<AccountRequestWithRole> createUser(@Valid @RequestBody AccountRequestWithRole accountRequest) {
        return ResponseEntity.ok(userServiceImpl.saveUserWithRole(accountRequest));
    }

}
