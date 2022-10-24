package com.bubbles.bubbles_backend.repo;

import com.bubbles.bubbles_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUserId(Integer userId);
    User findUserByUsername(String username);
    List<User> findUserByUserIdIn(List<Integer> ids);
    Boolean existsUserByUsername(String username);
}
