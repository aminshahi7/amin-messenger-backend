package com.aminmessenger.mymessenger.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aminmessenger.mymessenger.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}