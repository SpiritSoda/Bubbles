package com.bubbles.bubbles_backend.service;

import com.bubbles.bubbles_backend.config.BubblesConfig;
import com.bubbles.bubbles_backend.entity.BanRecord;
import com.bubbles.bubbles_backend.repo.BanRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BanRecordService {
    private final BanRecordRepository banRecordRepository;
    private final BubblesConfig bubblesConfig;

    @Autowired
    public BanRecordService(BanRecordRepository banRecordRepository, BubblesConfig bubblesConfig) {
        this.banRecordRepository = banRecordRepository;
        this.bubblesConfig = bubblesConfig;
    }
    public boolean existsByUserAndChatroom(int userId, int chatroomId){
        BanRecord record = banRecordRepository.findBanRecordByUserAndAndChatroom(userId, chatroomId);
        if(record.getEndTimestamp() < System.currentTimeMillis() / 1000l){
            banRecordRepository.removeBanRecordById(record.getId());
            return false;
        }
        else
            return true;
    }
    public void banUserFromChatroom(int userId, int chatroomId){
        BanRecord record = new BanRecord();
        record.setUser(userId);
        record.setChatroom(chatroomId);
        record.setEndTimestamp(System.currentTimeMillis() / 1000l + bubblesConfig.getInviteTokenExceedTime());
        banRecordRepository.save(record);
    }
}
