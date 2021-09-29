package com.pharmacy.v3.Services;

import com.pharmacy.v3.Models.Role;
import com.pharmacy.v3.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(Role newRole) {
        Role role = new Role(newRole.getRole());
        return roleRepository.save(role);
    }

    public Role getRoleByName(String roleName) {
        return roleRepository.findByRole(roleName);
    }

}
