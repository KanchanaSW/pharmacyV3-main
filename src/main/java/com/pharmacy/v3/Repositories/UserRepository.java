package com.pharmacy.v3.Repositories;

import com.pharmacy.v3.Models.Role;
import com.pharmacy.v3.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String name);

    User findByEmail(String email);
    List<User> findByRole(Role role);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.username like ?1%")
    User findUserByUsername(String username);

}
