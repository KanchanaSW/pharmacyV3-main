package com.pharmacy.v3.Controllers;

import com.pharmacy.v3.DTO.UserDTO;
import com.pharmacy.v3.Models.User;
import com.pharmacy.v3.Response.MessageResponse;
import com.pharmacy.v3.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/users")
@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/user/{userId}")
    public ResponseEntity<?> getAUser(@PathVariable Integer userId) {
        User user= userService.findUser(userId);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping(value = "/update-user/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Integer userId, @RequestBody UserDTO userDTO) {
        User user= userService.updateUser(userDTO);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/update-status/{userId}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer userId) {
        User user= userService.updateStatus(userId);
        return ResponseEntity.ok(user);
    }
}
