package com.bubbles.bubbles_backend.service;

import com.bubbles.bubbles_backend.dto.ChatroomDTO;
import com.bubbles.bubbles_backend.entity.Chatroom;
import com.bubbles.bubbles_backend.entity.User;
import com.bubbles.bubbles_backend.exception.ChatroomFullException;
import com.bubbles.bubbles_backend.exception.ChatroomNotFoundException;
import com.bubbles.bubbles_backend.exception.UserHasJoinedChatroomException;
import com.bubbles.bubbles_backend.exception.UserNotFoundException;
import com.bubbles.bubbles_backend.repo.ChatroomRepository;
import com.bubbles.bubbles_backend.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ChatroomService {
    private final ChatroomRepository chatroomRepository;
    private final UserRepository userRepository;
    @Autowired
    ChatroomService(ChatroomRepository chatroomRepository, UserRepository userRepository){
        this.chatroomRepository = chatroomRepository;
        this.userRepository = userRepository;
    }

    public Chatroom findById(int id) throws Exception{
        Chatroom chatroom = chatroomRepository.findChatroomByChatroomId(id);
        if(chatroom == null)
            throw new ChatroomNotFoundException(id);
        return chatroom;
    }
    public int joinChatroom(User user, Chatroom chatroom) throws Exception{
        if(user == null)
            throw new UserNotFoundException(-1);
        if(chatroom == null)
            throw new ChatroomNotFoundException(-1);
        if(chatroom.getUsers().size() >= chatroom.getMaxUser())
            throw new ChatroomFullException(chatroom.getChatroomId());
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
        return joinChatroom(user, chatroom);
    }
    public int joinChatroom(User user, int chatroomId) throws Exception{
        if(user == null)
            throw new UserNotFoundException(-1);
        Chatroom chatroom = chatroomRepository.findChatroomByChatroomId(chatroomId);
        if(chatroom == null)
            throw new ChatroomNotFoundException(chatroomId);
        return joinChatroom(user, chatroom);
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
    public void leaveChatroom(User user, int chatroomId) throws Exception {
        Chatroom chatroom = chatroomRepository.findChatroomByChatroomId(chatroomId);
        if(chatroom == null)
            throw new ChatroomNotFoundException(chatroomId);
        leaveChatroom(user, chatroom);
    }
    public void leaveChatroom(User user, Chatroom chatroom) throws Exception{
        if(user == null)
            throw new UserNotFoundException(-1);
        if(chatroom == null)
            throw new ChatroomNotFoundException(-1);
        user.getChatrooms().remove(chatroom);
        userRepository.save(user);
    }
    public boolean existChatroomById(int id){
        return chatroomRepository.existsById(id);
    }
    /*
        Whether a user joins given chatroom
     */
    public boolean inChatroom(User user, int chatroomId) throws ChatroomNotFoundException {
        Chatroom chatroom = chatroomRepository.findChatroomByChatroomId(chatroomId);
        if(chatroom == null)
            throw new ChatroomNotFoundException(chatroomId);
        List<Chatroom> joined = user.getChatrooms();
        if(!joined.contains(chatroom))
            return false;
        else
            return true;
    }
}
