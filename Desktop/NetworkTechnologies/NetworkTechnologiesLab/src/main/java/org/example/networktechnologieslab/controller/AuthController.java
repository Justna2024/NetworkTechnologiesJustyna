package org.example.networktechnologieslab.controller;


import org.example.networktechnologieslab.controller.datatransferobjects.LoginDto;
import org.example.networktechnologieslab.controller.datatransferobjects.LoginResponseDto;
import org.example.networktechnologieslab.controller.datatransferobjects.RegisterDto;
import org.example.networktechnologieslab.controller.datatransferobjects.RegisterResponseDto;
import org.example.networktechnologieslab.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// application programming interface that allows for data transfer between applications
@RestController
@RequestMapping("/api/auth")

public class AuthController {
    // controller when we create end points
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    private final AuthService authService;
    // preauth decides whether the person
    // is allowed to carry out the function that they are requesting

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')") // only admin can create an account for a person
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterDto requestBody){
        RegisterResponseDto dto = authService.register(requestBody);
        return new ResponseEntity(dto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @PreAuthorize("permitAll()") // but person can just log in
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto requestBody){
        LoginResponseDto dto = authService.login(requestBody);
        return new ResponseEntity(dto, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        authService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
