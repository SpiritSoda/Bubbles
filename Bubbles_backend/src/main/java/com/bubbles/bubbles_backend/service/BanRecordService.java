package com.bubbles.bubbles_backend.service;

import com.bubbles.bubbles_backend.config.BubblesConfig;
import com.bubbles.bubbles_backend.entity.BanRecord;
import com.bubbles.bubbles_backend.repo.BanRecordRepository;
import com.bubbles.bubbles_backend.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class BanRecordService {
    private final BanRecordRepository banRecordRepository;
    private final ChatroomService chatroomService;
    private final BubblesConfig bubblesConfig;

    @Autowired
    public BanRecordService(BanRecordRepository banRecordRepository, ChatroomService chatroomService, BubblesConfig bubblesConfig) {
        this.banRecordRepository = banRecordRepository;
        this.chatroomService = chatroomService;
        this.bubblesConfig = bubblesConfig;
    }
    public boolean existsByUserAndChatroom(int userId, int chatroomId){
        BanRecord record = banRecordRepository.findBanRecordByUserAndAndChatroom(userId, chatroomId);
        if(record == null)
            return false;
        if(record.getEndTimestamp() < TimeUtils.timestamp()){
            clearRecord(userId, chatroomId);
            return false;
        }
        else
            return true;
    }
    public void banUserFromChatroom(int userId, int chatroomId) throws Exception {
        clearRecord(userId, chatroomId);
        BanRecord record = new BanRecord();
        record.setUser(userId);
        record.setChatroom(chatroomId);
        record.setEndTimestamp(System.currentTimeMillis() + bubblesConfig.getInviteTokenExceedTime());
        banRecordRepository.save(record);
        chatroomService.leaveChatroom(userId, chatroomId);
    }
    public void clearRecord(int userId, int chatroomId){
        banRecordRepository.deleteBanRecordsByUserAndChatroom(userId, chatroomId);
    }
}
