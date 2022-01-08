package com.pharmacy.v3.Controllers;

import com.pharmacy.v3.DTO.UserDTO;
import com.pharmacy.v3.Models.User;
import com.pharmacy.v3.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public List<UserDTO> getAllUsers() {
        List<UserDTO> list=new ArrayList<>();
        for (User user:userService.getAllUsers()){
            UserDTO u=new UserDTO();
            u.setUserId(user.getUserId());
            u.setUsername(user.getUsername());
            u.setEmail(user.getEmail());
            u.setPhone(user.getPhone());
            u.setStatus(user.getStatus());
            u.setRole(user.getRole().getRole());
            list.add(u);
        }
        return list;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/user")
    public ResponseEntity<?> getAUser(Authentication authentication) {
        User userType = userService.directUserType(authentication.getName());
        User u=userService.findUser(userType.getUserId());
        User user= userService.findUser(u.getUserId());
        UserDTO u1=new UserDTO();
        if (user != null){
            u1.setUserId(user.getUserId());
            u1.setUsername(user.getUsername());
            u1.setEmail(user.getEmail());
            u1.setPhone(user.getPhone());
        }
        return ResponseEntity.ok(u1);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/update-user")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
        User user= userService.updateUser(userDTO);
        return ResponseEntity.ok("Success");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/update-status/{userId}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer userId) {
        User user= userService.updateStatus(userId);
        return ResponseEntity.ok(user);
    }
}
