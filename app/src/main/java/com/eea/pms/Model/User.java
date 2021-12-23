package com.eea.pms.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer userId;
    private String email, username, password, phone,status,role;

    public User(String email, String username, String password, String phone) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
    }
    public User(Integer userId,String email, String username, String phone,String status,String role){
        this.userId=userId;
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.status=status;
        this.role=role;
    }
}
