package com.example.bookstore.service.impl;

import com.example.bookstore.dto.UserRegistrationRequestDto;
import com.example.bookstore.dto.UserResponseDto;
import com.example.bookstore.exception.EntityNotFoundException;
import com.example.bookstore.exception.RegistrationException;
import com.example.bookstore.mapper.UserMapper;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException(
                    "Can't register a new user with email: "
                    + requestDto.getEmail()
            );
        }
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        User user = userMapper.toModel(requestDto);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find user by id: " + id)
                );
    }
}
