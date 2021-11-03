package com.pharmacy.v3.Controllers;

import com.pharmacy.v3.DTO.UserDTO;
import com.pharmacy.v3.Models.User;
import com.pharmacy.v3.Request.AuthRequest;
import com.pharmacy.v3.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/auth")
@Controller
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO registerUser) {
        return authService.registerUserService(registerUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody AuthRequest authRequest) {
        return authService.loginUserService(authRequest);
    }
}
