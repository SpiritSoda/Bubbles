package com.bubbles.bubbles_backend.controller;

import com.bubbles.bubbles_backend.component.ChatroomManager;
import com.bubbles.bubbles_backend.config.JwtConfig;
import com.bubbles.bubbles_backend.dto.ChatroomDTO;
import com.bubbles.bubbles_backend.dto.UserChatroomDTO;
import com.bubbles.bubbles_backend.dto.PassportDTO;
import com.bubbles.bubbles_backend.entity.Chatroom;
import com.bubbles.bubbles_backend.entity.User;
import com.bubbles.bubbles_backend.exception.*;
import com.bubbles.bubbles_backend.service.BanRecordService;
import com.bubbles.bubbles_backend.service.ChatroomService;
import com.bubbles.bubbles_backend.service.PassportService;
import com.bubbles.bubbles_backend.service.UserService;
import com.bubbles.bubbles_backend.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class ChatroomController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatroomService chatroomService;
    private final UserService userService;
    private final PassportService passportService;
    private final ChatroomManager chatroomManager;
    private final JwtConfig jwtConfig;
    private final BanRecordService banRecordService;

    @Autowired
    public ChatroomController(SimpMessagingTemplate simpMessagingTemplate, ChatroomService chatroomService, UserService userService, PassportService passportService, ChatroomManager chatroomManager, JwtConfig jwtConfig, BanRecordService banRecordService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatroomService = chatroomService;
        this.userService = userService;
        this.passportService = passportService;
        this.chatroomManager = chatroomManager;
        this.jwtConfig = jwtConfig;
        this.banRecordService = banRecordService;
    }

    @PostMapping("/api/chatroom/verifyPassport")
    public Result verifyPassport(@RequestBody @Valid PassportDTO passportDTO){
        if(!passportService.verify(passportDTO.getToken()))
            return Result.buildFailResult("Invalid Passport");
        int maxUser = ChatroomPassportUtils.getMaxUser(passportDTO.getToken(), jwtConfig);
        HashMap<String, Object> data = new HashMap<>();
        data.put("max_user", maxUser);
        return Result.buildSuccessResult("Verify Success", data);
    }
    @GetMapping("/api/chatroom/generatePassport")
    public Result generate(){
        String token = ChatroomPassportUtils.createChatroomPassport(30, jwtConfig);
        passportService.save(token, PassportType.CREATE_CHATROOM);
        HashMap<String, Object> data = new HashMap<>();
        data.put("passport", token);
        return Result.buildSuccessResult("Generate Success", data);
    }
    @PostMapping("/api/chatroom/createChatroom")
    public Result createChatroom(@RequestBody @Valid ChatroomDTO chatroomDTO, HttpServletRequest request) throws Exception {
        String token = request.getHeader("token");
        User user = userService.findByToken(token);
        if(!passportService.consume(chatroomDTO.getPassport()))
            throw new PassportNotValidException(chatroomDTO.getPassport());
        chatroomDTO.setAdmin(user.getUserId());
        Chatroom chatroom = chatroomService.createChatroom(chatroomDTO);
        chatroomService.joinChatroom(user, chatroom);
        return Result.buildSuccessResult("Success to create chatroom");
    }

    @PostMapping("/api/chatroom/inviteToken")
    public Result generateInviteToken(@RequestBody @Valid ChatroomDTO chatroomDTO, HttpServletRequest request) throws Exception{
        int id = chatroomDTO.getId();
        if(!chatroomService.existChatroomById(id)){
            throw new ChatroomNotFoundException(id);
        }
        String token = request.getHeader("token");
        User user = userService.findByToken(token);
        if(!chatroomService.inChatroom(user, id)){
            throw new UserNotInChatroomException(user.getUserId(), id);
        }
        token = InviteTokenUtils.generateInviteToken(id, jwtConfig);

        HashMap<String, Object> data = new HashMap<>();
        data.put("token", token);
        return Result.buildSuccessResult("Generate Success", data);
    }

    @PostMapping("/api/chatroom/join")
    public Result joinChatroom(@RequestBody @Valid PassportDTO passportDTO, HttpServletRequest request) throws Exception {
        String token = passportDTO.getToken();
        int id = InviteTokenUtils.verifyInviteToken(token, jwtConfig);
        if(id == 0)
            throw new PassportNotValidException(token);
        token = request.getHeader("token");
        User user = userService.findByToken(token);
        if(banRecordService.existsByUserAndChatroom(user.getUserId(), id))
            throw new UserBannedException(user.getUserId(), id);
        chatroomService.joinChatroom(user, id);
        return Result.buildSuccessResult("Success to join chatroom");
    }
    @PostMapping("/api/chatroom/invite")
    public Result inviteToChatroom(@RequestBody @Valid UserChatroomDTO userChatroomDTO, HttpServletRequest request) throws Exception {
        String token = request.getHeader("token");
        User user = userService.findByToken(token);
        if(!chatroomService.inChatroom(user, userChatroomDTO.getChatroomId()))
            throw new UserNotInChatroomException(user.getUserId(), userChatroomDTO.getChatroomId());
        boolean adminInvite = chatroomService.isAdmin(user, userChatroomDTO.getChatroomId());
        boolean gotBanned = banRecordService.existsByUserAndChatroom(userChatroomDTO.getUserId(), userChatroomDTO.getChatroomId());
        if(!adminInvite && gotBanned)
            throw new UserBannedException(userChatroomDTO.getUserId(), userChatroomDTO.getChatroomId());
        if(adminInvite && gotBanned)
            banRecordService.clearRecord(userChatroomDTO.getUserId(), userChatroomDTO.getChatroomId());
        chatroomService.joinChatroom(userChatroomDTO.getUserId(), userChatroomDTO.getChatroomId());
        return Result.buildSuccessResult("Success to join chatroom");
    }
    @PostMapping("/api/chatroom/leave")
    public Result leaveChatroom(@RequestBody @Valid ChatroomDTO chatroomDTO, HttpServletRequest request) throws Exception{
        String token = request.getHeader("token");
        User user = userService.findByToken(token);
        chatroomService.leaveChatroom(user, chatroomDTO.getId());
        return Result.buildSuccessResult("Success to leave chatroom");
    }

    @PostMapping("/api/chatroom/onlines")
    public Result getOnlineUsers(@RequestBody @Valid ChatroomDTO chatroomDTO, HttpServletRequest request) throws Exception{
        String token = request.getHeader("token");
        User user = userService.findByToken(token);
        if(!chatroomService.inChatroom(user, chatroomDTO.getId()))
            throw new UserNotInChatroomException(user.getUserId(), chatroomDTO.getId());
        List<Integer> onlines = chatroomManager.chatroomOnline(chatroomService.findById(chatroomDTO.getId()));
        HashMap<String, Object> data = new HashMap<>();
        data.put("onlines", onlines);
        return Result.buildSuccessResult("Success to get online user", data);
    }
    @PostMapping("/api/chatroom/ban")
    public Result banUser(@RequestBody @Valid UserChatroomDTO userChatroomDTO, HttpServletRequest request) throws Exception{
        String token = request.getHeader("token");
        User user = userService.findByToken(token);
        if(!chatroomService.inChatroom(user, userChatroomDTO.getChatroomId()))
            throw new UserNotInChatroomException(user.getUserId(), userChatroomDTO.getChatroomId());
        if(!chatroomService.isAdmin(user, userChatroomDTO.getChatroomId()))
            return Result.buildFailResult("You are not chatroom\'s admin");
        if(chatroomService.isAdmin(userChatroomDTO.getUserId(), userChatroomDTO.getChatroomId()))
            return Result.buildFailResult("You can\'t ban yourself");
        banRecordService.banUserFromChatroom(userChatroomDTO.getUserId(), userChatroomDTO.getChatroomId());
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(userChatroomDTO.getUserId()), "/receive", STOMPMessage.buildMessage(STOMPMessageType.BANNED_MESSAGE, 0));
        return Result.buildSuccessResult("Success to ban user");
    }
}
