package com.ascherty.app.controllers;

import com.ascherty.app.model.dto.UserDTO;
import com.ascherty.app.model.dto.UserLoginDTO;
import com.ascherty.app.model.dto.UserRegisterDTO;
import com.ascherty.app.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * API for login and registration.
 */

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        return ResponseEntity.ok(authService.registerUser(userRegisterDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userLoginDTO) {
        return ResponseEntity.ok(authService.login(userLoginDTO));
    }

//    response.addCookie(ResponseCookie.from("jwt", token)
//            .httpOnly(true)
//    .secure(true) // Только HTTPS
//    .sameSite("Strict") // Запрещаем отправку с чужих сайтов
//    .path("/")
//    .build());
}
