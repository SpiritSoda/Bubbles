package com.bubbles.bubbles_backend.service;

import com.bubbles.bubbles_backend.dto.ChatroomDTO;
import com.bubbles.bubbles_backend.entity.Chatroom;
import com.bubbles.bubbles_backend.entity.User;
import com.bubbles.bubbles_backend.exception.ChatroomNotFoundException;
import com.bubbles.bubbles_backend.exception.UserHasJoinedChatroomException;
import com.bubbles.bubbles_backend.exception.UserNotFoundException;
import com.bubbles.bubbles_backend.repo.ChatroomRepository;
import com.bubbles.bubbles_backend.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatroomService {
    private final ChatroomRepository chatroomRepository;
    private final UserRepository userRepository;
    @Autowired
    ChatroomService(ChatroomRepository chatroomRepository, UserRepository userRepository){
        this.chatroomRepository = chatroomRepository;
        this.userRepository = userRepository;
    }

    public int joinChatroom(User user, Chatroom chatroom) throws Exception{
        if(user == null)
            throw new UserNotFoundException(-1);
        if(chatroom == null)
            throw new ChatroomNotFoundException(-1);
        List<Chatroom> joined = user.getChatrooms();
        if(joined.contains(chatroom))
            throw new UserHasJoinedChatroomException(user.getUserId(), chatroom.getChatroomId());
        user.getChatrooms().add(chatroom);
        userRepository.save(user);
        return 0;
    }
    public int joinChatroom(int userId, int chatroomId) throws Exception{
        User user = userRepository.findUserByUserId(userId);
        Chatroom chatroom = chatroomRepository.findChatroomByChatroomId(chatroomId);
        if(user == null)
            throw new UserNotFoundException(userId);
        if(chatroom == null)
            throw new ChatroomNotFoundException(chatroomId);
        joinChatroom(user, chatroom);
        return 0;
    }
    public Chatroom createChatroom(ChatroomDTO chatroomDTO){
        Chatroom chatroom = new Chatroom();
        chatroom.setAdmin(chatroomDTO.getAdmin());
        chatroom.setTitle(chatroomDTO.getTitle());
        chatroom.setIcon(chatroomDTO.getIcon());
        chatroom.setComment(chatroomDTO.getComment());
        chatroom.setMaxUser(chatroomDTO.getMaxUser());
        return chatroomRepository.save(chatroom);
    }
}
