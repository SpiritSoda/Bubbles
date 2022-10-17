package com.bubbles.bubbles_backend.repo;

import com.bubbles.bubbles_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUserId(Integer userId);
    User findUserByUsername(String username);
    Boolean existsUserByUsername(String username);
}
