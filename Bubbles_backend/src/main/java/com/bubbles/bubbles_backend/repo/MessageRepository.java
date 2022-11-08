package com.bubbles.bubbles_backend.repo;

import com.bubbles.bubbles_backend.entity.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findAll(Specification<Message> specification, Pageable pageable);
}
