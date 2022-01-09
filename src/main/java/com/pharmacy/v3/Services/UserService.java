package com.pharmacy.v3.Services;

import com.pharmacy.v3.DTO.UserDTO;
import com.pharmacy.v3.Models.OTP;
import com.pharmacy.v3.Models.User;
import com.pharmacy.v3.Repositories.OTPRepository;
import com.pharmacy.v3.Repositories.RoleRepository;
import com.pharmacy.v3.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User updateUser(UserDTO userDTO){
        Optional<User> user = userRepository.findById(userDTO.getUserId());
        User u=user.get();
        //u.setEmail(userDTO.getEmail());
        u.setPhone(userDTO.getPhone());
        u.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(u);
    }
    public User updateStatus(Integer userId){
        User u=findUser(userId);
        u.setStatus("verified");
        u=userRepository.save(u);
       return u;
    }

    public User getUserByUserName(String username) {
        return userRepository.findByUsername(username).get();
    }

    public User findUser(Integer userId){
                Optional<User> user = userRepository.findById(userId);
                User u = null;
                if(user.isPresent()){
                    u = user.get();
                }
                return u;
    }

    public User directUserType(String username){
        return userRepository.findByUsername(username).get();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Integer userID) {
       /* if (otpRepository.existsByUserUserId(userID)){
           OTP otp= otpRepository.findByUserUserId(userID);
           otpRepository.delete(otp);
        }*/
        Optional<User> user = userRepository.findById(userID);
        User u = null;

        try {
            if (otpRepository.existsByUserUserId(userID)) {
                OTP otp = otpRepository.findByUserUserId(userID);
                otpRepository.delete(otp);
            }
        } finally {
            if(user.isPresent()){
                u = user.get();
                userRepository.delete(u);
            }
        }
    }
    public void delete(User user){
        try {
            if (otpRepository.existsByUserUserId(user.getUserId())) {
                OTP otp = otpRepository.findByUserUserId(user.getUserId());
                otpRepository.delete(otp);
            }
        } finally {
            userRepository.delete(user);
        }
    }


}
