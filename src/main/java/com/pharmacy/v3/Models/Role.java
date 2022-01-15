package com.pharmacy.v3.Models;

import lombok.Builder;

import javax.persistence.*;

@Entity
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;
    private String role;

    public Role(){}
    public Role(Integer roleId, String role) {
        this.roleId = roleId;
        this.role = role;
    }

    public Role(String role) {
        this.role = role;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
