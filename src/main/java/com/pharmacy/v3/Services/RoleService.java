package com.pharmacy.v3.Services;

import com.pharmacy.v3.Models.Role;
import com.pharmacy.v3.Models.User;
import com.pharmacy.v3.Repositories.RoleRepository;
import com.pharmacy.v3.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    public Role createRole(Role newRole) {
        Role role = new Role(newRole.getRole());
        return roleRepository.save(role);
    }

    public Role getRoleByName(String roleName) {
        return roleRepository.findByRole(roleName);
    }


}
