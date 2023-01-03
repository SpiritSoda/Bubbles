package com.bubbles.bubbles_backend.repo;

import com.bubbles.bubbles_backend.entity.BanRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BanRecordRepository extends JpaRepository<BanRecord, Integer> {
    BanRecord findBanRecordByUserAndAndChatroom(int userId, int chatroomId);
    void deleteBanRecordById(int id);
    void deleteBanRecordsByUserAndChatroom(int userId, int chatroomId);
}
