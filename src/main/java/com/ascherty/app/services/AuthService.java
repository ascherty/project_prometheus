package com.ascherty.app.services;

import com.ascherty.app.model.dto.UserDTO;
import com.ascherty.app.model.dto.UserLoginDTO;
import com.ascherty.app.model.dto.UserRegisterDTO;
import com.ascherty.app.model.entity.User;
import com.ascherty.app.repository.UserRepository;
import com.ascherty.app.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * service for login and registration.
 */

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserDTO registerUser(UserRegisterDTO userRegisterDTO) {
        if (userRepository.findByUsername(userRegisterDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }
        User user = modelMapper.map(userRegisterDTO, User.class);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    public String login(UserLoginDTO userLoginDTO) {
        User user = userRepository.findByUsername(userLoginDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials!");
        }
        return jwtUtil.generateToken(user.getUsername(), user.getRole());
    }
}
