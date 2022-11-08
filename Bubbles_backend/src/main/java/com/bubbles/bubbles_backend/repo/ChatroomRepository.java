package com.bubbles.bubbles_backend.repo;

import com.bubbles.bubbles_backend.entity.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomRepository extends JpaRepository<Chatroom, Integer> {
}
