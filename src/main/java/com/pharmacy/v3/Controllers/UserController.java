package com.pharmacy.v3.Controllers;

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
        return userService.getUserByUserId(userId);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping(value = "/reset-password/{userId}")
    public ResponseEntity<MessageResponse> updateUserPasswordByUserId(@PathVariable Integer userId, @RequestBody String newPassword) {
        return userService.updateUserPasswordByUserId(userId, newPassword);
    }
}
