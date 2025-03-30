package com.ascherty.app.services;

import com.ascherty.app.model.dto.UserDTO;
import com.ascherty.app.model.entity.User;
import com.ascherty.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * User management service.
 */

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserDTO getUserByName(String name) {
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        return modelMapper.map(user, UserDTO.class);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        user.setUsername(userDTO.getUsername());
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}


