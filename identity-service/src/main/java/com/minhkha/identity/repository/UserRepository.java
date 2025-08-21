package com.minhkha.identity.repository;

import com.minhkha.identity.entity.User;
import com.minhkha.identity.eums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsUserByRole(Role role);

    boolean existsUserByEmail(String email);

    Optional<User> findUserByEmail(String email);
}
