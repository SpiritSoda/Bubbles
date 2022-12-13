package com.bubbles.bubbles_backend.controller;

import com.bubbles.bubbles_backend.config.JwtConfig;
import com.bubbles.bubbles_backend.dto.ChatroomDTO;
import com.bubbles.bubbles_backend.dto.InviteDTO;
import com.bubbles.bubbles_backend.dto.PassportDTO;
import com.bubbles.bubbles_backend.entity.Chatroom;
import com.bubbles.bubbles_backend.entity.User;
import com.bubbles.bubbles_backend.exception.*;
import com.bubbles.bubbles_backend.service.ChatroomService;
import com.bubbles.bubbles_backend.service.PassportService;
import com.bubbles.bubbles_backend.service.UserService;
import com.bubbles.bubbles_backend.utils.ChatroomPassportUtils;
import com.bubbles.bubbles_backend.utils.InviteTokenUtils;
import com.bubbles.bubbles_backend.utils.PassportType;
import com.bubbles.bubbles_backend.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@Slf4j
public class ChatroomController {
    private final ChatroomService chatroomService;
    private final UserService userService;
    private final PassportService passportService;
    private final JwtConfig jwtConfig;

    @Autowired
    public ChatroomController(ChatroomService chatroomService, UserService userService, PassportService passportService, JwtConfig jwtConfig) {
        this.chatroomService = chatroomService;
        this.userService = userService;
        this.passportService = passportService;
        this.jwtConfig = jwtConfig;
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
        chatroomService.joinChatroom(user, id);
        return Result.buildSuccessResult("Success to join chatroom");
    }
    @PostMapping("/api/chatroom/invite")
    public Result inviteToChatroom(@RequestBody @Valid InviteDTO inviteDTO, HttpServletRequest request) throws Exception {
        String token = request.getHeader("token");
        User user = userService.findByToken(token);
        if(!chatroomService.inChatroom(user, inviteDTO.getChatroomId()))
            throw new UserNotInChatroomException(user.getUserId(), inviteDTO.getChatroomId());
        chatroomService.joinChatroom(inviteDTO.getUserId(), inviteDTO.getChatroomId());
        return Result.buildSuccessResult("Success to join chatroom");
    }
    @PostMapping("/api/chatroom/leave")
    public Result leaveChatroom(@RequestBody @Valid ChatroomDTO chatroomDTO, HttpServletRequest request) throws Exception{
        String token = request.getHeader("token");
        User user = userService.findByToken(token);
        chatroomService.leaveChatroom(user, chatroomDTO.getId());
        return Result.buildSuccessResult("Success to leave chatroom");
    }
}
