package com.bubbles.bubbles_backend.repo;

import com.bubbles.bubbles_backend.entity.Passport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassportRepository extends JpaRepository<Passport, Integer> {
    Passport findByToken(String token);
}
