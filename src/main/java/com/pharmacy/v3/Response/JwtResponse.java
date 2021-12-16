package com.pharmacy.v3.Response;


import java.util.Date;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Integer id;
    private String username;
    private String email;
    private String roles;
    private String status;
    private Date tokenExpireTime;

    public JwtResponse(String token, Integer id, String username, String email, String roles, Date tokenExpireTime,String status) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.tokenExpireTime = tokenExpireTime;
        this.status=status;
    }
public JwtResponse(){}


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Date getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(Date tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }
}
