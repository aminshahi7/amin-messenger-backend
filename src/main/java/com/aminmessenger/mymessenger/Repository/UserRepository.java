package com.aminmessenger.mymessenger.Repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aminmessenger.mymessenger.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    @Query(value=""" 
       SELECT u.*
       FROM users u
       JOIN user_contacts c ON u.id = c.contact_id
       WHERE c.user_id = :userId
    """, nativeQuery = true)
    Set<User> findAllContactByUserId(@Param ("userId") Long userId);
}