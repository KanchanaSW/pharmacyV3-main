package com.pharmacy.v3.Services;

import com.pharmacy.v3.Models.Role;
import com.pharmacy.v3.Models.User;
import com.pharmacy.v3.Repositories.UserRepository;
import com.pharmacy.v3.Request.AuthRequest;
import com.pharmacy.v3.Response.JwtResponse;
import com.pharmacy.v3.Response.MessageResponse;
import com.pharmacy.v3.Security.Service.UserDetailsImpl;
import com.pharmacy.v3.Security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private RoleService roleService;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtUtils jwtUtils;

    @Autowired
    public AuthService(RoleService roleService, UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;

    }

    public ResponseEntity<?> registerUserService(User registerUser) {
        try {
            if (userRepository.existsByUsername(registerUser.getUsername())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Username already taken!"));
            }
            if (userRepository.existsByEmail(registerUser.getEmail())) {
                return ResponseEntity.badRequest().body(new MessageResponse(("Email already taken!")));
            }
            //create a new user account after the checking
            User user = new User(
                    registerUser.getUsername(),
                    registerUser.getEmail(),
                    registerUser.getPhone(),
                    passwordEncoder.encode(registerUser.getPassword())
            );

            Role role = roleService.getRoleByName("ROLE_USER");
            user.setRole(role);
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("User registered successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error")));
        }
    }

    public ResponseEntity<?> loginUserService(AuthRequest authRequest) {
        try {
            if (!userRepository.existsByUsername(authRequest.getUsername())) {
                return ResponseEntity.ok("User name doesn't exist");
            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken((authentication));
            Date expiretime = jwtUtils.expirationTime();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles.get(0), expiretime));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error")));
        }
    }
}
