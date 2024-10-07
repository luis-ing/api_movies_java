package com.movies.api.Jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.movies.api.models.Role;
import com.movies.api.models.User;
import com.movies.api.repositories.UserRepository;
import com.movies.api.requestModels.LoginRequest;
import com.movies.api.requestModels.RegisterRequest;
import com.movies.api.responseModels.AuthResponse;
import com.movies.api.responseModels.UserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

        private final UserRepository userRepository;
        private final JwtService jwtService;
        private final PasswordEncoder passwordEncoder;
        private final AuthenticationManager authenticationManager;

        public AuthResponse login(LoginRequest request) {
                authenticationManager
                                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                                                request.getPassword()));
                User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
                String token = jwtService.getToken(user);

                UserResponse userResponse = UserResponse.builder()
                                .id(user.getId())
                                .name(user.getName())
                                .username(user.getUsername())
                                .role(user.getRole())
                                .build();

                return AuthResponse.builder()
                                .token(token)
                                .user(userResponse)
                                .build();
        }

        public AuthResponse register(RegisterRequest request) {
                User user = User.builder()
                                .name(request.getName())
                                .username(request.getUsername())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .isActive(true)
                                .role(Role.USER)
                                .build();

                userRepository.save(user);

                return AuthResponse.builder()
                                .token(jwtService.getToken(user))
                                .build();
        }

}
