package com.pharmacy.v3.Services;

import com.pharmacy.v3.Models.User;
import com.pharmacy.v3.Repositories.UserRepository;
import com.pharmacy.v3.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> getUserByUserId(Integer userId) {
        try {
            if (userRepository.existsById(userId)) {
                Optional<User> user = userRepository.findById(userId);
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.badRequest().body(new MessageResponse("User not found!!!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    public ResponseEntity<MessageResponse> updateUserPasswordByUserId(Integer userId, String newPassword) {
        try {
            if (userRepository.existsById(userId)) {
                User user = userRepository.findById(userId).get();
                user.setPassword(newPassword);
                userRepository.save(user);
                return ResponseEntity.ok(new MessageResponse("Successfully Updated"));
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("User not available!"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    public User getUserByUserName(String username) {
        return userRepository.findByUsername(username).get();
    }

    public User directUserType(String username){
        return userRepository.findUserByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
