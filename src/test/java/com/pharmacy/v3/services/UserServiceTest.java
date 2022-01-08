package com.pharmacy.v3.services;

import com.pharmacy.v3.DTO.UserDTO;
import com.pharmacy.v3.Models.User;
import com.pharmacy.v3.Services.UserService;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getAllUsers(){
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
        assertThat(list,not(IsEmptyCollection.empty()));
    }
}
