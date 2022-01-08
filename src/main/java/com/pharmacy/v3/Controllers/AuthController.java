package com.pharmacy.v3.Controllers;

import com.pharmacy.v3.DTO.UserDTO;
import com.pharmacy.v3.Models.AuthRequest;
import com.pharmacy.v3.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO registerUser) {
        String roleName="ROLE_USER";
        return authService.registerUserService(registerUser,roleName);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody AuthRequest authRequest) {
        return authService.loginUserService(authRequest);
    }

/*    //Beta
    @PostMapping("/register/customer")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody UserDTO registerCUS) {
        String roleName="ROLE_CUSTOMER";
        return authService.registerUserService(registerCUS,roleName);
    }*/
}
