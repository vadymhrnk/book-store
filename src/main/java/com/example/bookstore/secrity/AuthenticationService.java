package com.example.bookstore.secrity;

import com.example.bookstore.dto.UserLoginRequestDto;
import com.example.bookstore.dto.UserLoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserLoginResponseDto authenticate(UserLoginRequestDto requestDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(),
                        requestDto.getPassword()
                )
        );

        String token = jwtUtil.generateToken(authentication.getName());
        UserLoginResponseDto responseDto = new UserLoginResponseDto();
        responseDto.setToken(token);
        return responseDto;
    }
}
