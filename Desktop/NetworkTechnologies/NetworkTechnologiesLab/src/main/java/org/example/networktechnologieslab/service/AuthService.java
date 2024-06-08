package org.example.networktechnologieslab.service;

import org.example.networktechnologieslab.controller.datatransferobjects.LoginDto;
import org.example.networktechnologieslab.controller.datatransferobjects.LoginResponseDto;
import org.example.networktechnologieslab.controller.datatransferobjects.RegisterDto;
import org.example.networktechnologieslab.controller.datatransferobjects.RegisterResponseDto;
import org.example.networktechnologieslab.infrastructure.entity.Auth;
import org.example.networktechnologieslab.infrastructure.entity.User;
import org.example.networktechnologieslab.infrastructure.repository.AuthRepository;
import org.example.networktechnologieslab.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AuthService {
    private final AuthRepository authRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    private final JwtService jwtService;

    @Autowired
    public AuthService(AuthRepository authRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    // adding so that another user with same name cant exist
    public RegisterResponseDto register(RegisterDto dto){
        Optional<Auth> existingAuth = authRepository.findByUsername(dto.getUsername());
        if (existingAuth.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with a given username exists");
        }
        User userEntity = new User();
        userEntity.setEmail(dto.getEmail());
        userRepository.save(userEntity);

        Auth authEntity = new Auth();
        authEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
        authEntity.setUsername(dto.getUsername());
        authEntity.setRole(dto.getRole());
        authEntity.setUser(userEntity);

        authRepository.save(authEntity);

        return new RegisterResponseDto(userEntity.getUserId(), authEntity.getUsername(), authEntity.getRole());
    }
    public LoginResponseDto login(LoginDto dto){
        Auth authEntity = authRepository.findByUsername(dto.getUsername()).orElseThrow(RuntimeException::new);

        if(!passwordEncoder.matches(dto.getPassword(), authEntity.getPassword())){
            return new LoginResponseDto("Wrong password");
        }

        String token = jwtService.generateToken(authEntity);


        return new LoginResponseDto(token);
    }
}
