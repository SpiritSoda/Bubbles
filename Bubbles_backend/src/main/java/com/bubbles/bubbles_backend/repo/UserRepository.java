package com.bubbles.bubbles_backend.repo;

import com.bubbles.bubbles_backend.entity.AnonymousUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnonymousUserRepository extends JpaRepository<AnonymousUser, Integer> {
    AnonymousUser findUserByUserId(Integer userId);
}
