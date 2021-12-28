package com.eea.pms.DTO.Responses;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private String id;
    private String username;
    private String email;
    private String roles;
    private String tokenExpireTime;
    private String status;


}
