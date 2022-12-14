package com.bubbles.bubbles_backend.controller;

import com.bubbles.bubbles_backend.dto.MessageDTO;
import com.bubbles.bubbles_backend.dto.MessageQueryDTO;
import com.bubbles.bubbles_backend.entity.Message;
import com.bubbles.bubbles_backend.entity.User;
import com.bubbles.bubbles_backend.exception.UserNotInChatroomException;
import com.bubbles.bubbles_backend.service.ChatroomService;
import com.bubbles.bubbles_backend.service.MessageService;
import com.bubbles.bubbles_backend.service.UserService;
import com.bubbles.bubbles_backend.utils.MessageType;
import com.bubbles.bubbles_backend.utils.Result;
import com.bubbles.bubbles_backend.utils.STOMPMessage;
import com.bubbles.bubbles_backend.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
public class ChatController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;
    private final UserService userService;
    private final ChatroomService chatroomService;
    @Autowired
    ChatController(SimpMessagingTemplate simpMessagingTemplate, MessageService messageService, UserService userService, ChatroomService chatroomService){
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageService = messageService;
        this.userService = userService;
        this.chatroomService = chatroomService;
    }
    @PostMapping("/api/chat/send")
    public Result sendMessage(@RequestBody @Valid MessageDTO messageDTO, HttpServletRequest request) throws Exception{
        String token = request.getHeader("token");
        User user = userService.findByToken(token);
        if(!chatroomService.inChatroom(user, messageDTO.getChatroomId())){
            throw new UserNotInChatroomException(user.getUserId(), messageDTO.getChatroomId());
        }
        messageDTO.setTimestamp(TimeUtils.timestamp());
        messageDTO.setSenderId(user.getUserId());
        Message message = messageService.saveMessage(messageDTO);
        simpMessagingTemplate.convertAndSend("/chat/chatroom/" + messageDTO.getChatroomId(), STOMPMessage.buildMessage(MessageType.BROADCAST_MESSAGE, message));

        return Result.buildSuccessResult("Success to send message");
    }
    @PostMapping("/api/chat/get")
    public Result getMessage(@RequestBody @Valid MessageQueryDTO messageQueryDTO, HttpServletRequest request) throws Exception{
        String token = request.getHeader("token");
        User user = userService.findByToken(token);
        if(!chatroomService.inChatroom(user, messageQueryDTO.getChatroomId())){
            throw new UserNotInChatroomException(user.getUserId(), messageQueryDTO.getChatroomId());
        }
        List<Message> messages = messageService.getMessage(messageQueryDTO);

        HashMap<String, Object> data = new HashMap<>();
        data.put("count", messages.size());
        data.put("msg", messages);
        return Result.buildSuccessResult("Success to get messages", data);
    }
}
