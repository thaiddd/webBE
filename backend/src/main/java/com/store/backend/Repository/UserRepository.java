package com.store.backend.Repository;

import com.store.backend.Entity.Role;
import com.store.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query(value = "select * from user where username = ?", nativeQuery = true)
    public User searchUserByUsername(String username);

    public Long countByRolesContains(Role role);
}
