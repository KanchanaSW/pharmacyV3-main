package com.pharmacy.v3.Security.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pharmacy.v3.Models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String username;
    private String email;
    private String status;
    @JsonIgnore
    private String password;
    private List<GrantedAuthority> authorities;

    public UserDetailsImpl(Integer id, String username, String email, String password, List<GrantedAuthority> authorities,String status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.status=status;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = Arrays.stream(user.getRole().getRole().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new UserDetailsImpl(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities,user.getStatus());

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) object;
        return Objects.equals(id, userDetails.id);
    }
}
