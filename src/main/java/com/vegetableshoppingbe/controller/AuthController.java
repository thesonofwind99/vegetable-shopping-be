package com.vegetableshoppingbe.controller;

import com.vegetableshoppingbe.dto.request.ChangePasswordRequest;
import com.vegetableshoppingbe.dto.request.LoginRequest;
import com.vegetableshoppingbe.service.AuthService;
import com.vegetableshoppingbe.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@CrossOrigin(value = "http://localhost:8081", exposedHeaders = {"X-Role", "X-User-Name"})
public class AuthController {

    private final AuthService authService;

    private final UserService userService;
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("X-Role", authService.findRoleNameByUsername(loginRequest.getUsername()));
        responseHeaders.set("X-User-Name", String.valueOf(userService.findByUsername(loginRequest.getUsername()).getUsername()));
        return ResponseEntity.ok().headers(responseHeaders).body(token);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> changePassword(@PathVariable Integer id,
                                            @Valid @RequestBody ChangePasswordRequest request) {
        authService.changePassword(id, request);
        return ResponseEntity.ok().body(request);
    }

}
