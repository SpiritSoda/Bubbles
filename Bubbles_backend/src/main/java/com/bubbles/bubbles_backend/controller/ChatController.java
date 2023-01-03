package com.bubbles.bubbles_backend.controller;

import com.bubbles.bubbles_backend.component.FileManager;
import com.bubbles.bubbles_backend.config.FileConfig;
import com.bubbles.bubbles_backend.dto.MessageDTO;
import com.bubbles.bubbles_backend.dto.MessageQueryDTO;
import com.bubbles.bubbles_backend.entity.Message;
import com.bubbles.bubbles_backend.entity.User;
import com.bubbles.bubbles_backend.exception.UserNotInChatroomException;
import com.bubbles.bubbles_backend.service.ChatroomService;
import com.bubbles.bubbles_backend.service.MessageService;
import com.bubbles.bubbles_backend.service.UserService;
import com.bubbles.bubbles_backend.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
public class ChatController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;
    private final UserService userService;
    private final ChatroomService chatroomService;
    private final FileConfig fileConfig;
    private final FileManager fileManager;
    @Autowired
    ChatController(SimpMessagingTemplate simpMessagingTemplate, MessageService messageService, UserService userService, ChatroomService chatroomService, FileConfig fileConfig, FileManager fileManager){
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageService = messageService;
        this.userService = userService;
        this.chatroomService = chatroomService;
        this.fileConfig = fileConfig;
        this.fileManager = fileManager;
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
        messageDTO.setType(MessageType.TEXT);
        Message message = messageService.saveMessage(messageDTO);
        simpMessagingTemplate.convertAndSend("/chat/chatroom/" + messageDTO.getChatroomId(), STOMPMessage.buildMessage(STOMPMessageType.BROADCAST_MESSAGE, message));

        return Result.buildSuccessResult("Success to send message");
    }
    @PostMapping("/api/chat/uploadFile")
    public Result uploadFile(@RequestParam("file")MultipartFile file, @RequestParam("chatroom")Integer chatroom, @RequestParam("type")Integer type, HttpServletRequest request) throws Exception {
        if(!MessageType.isValid(type))
            return Result.buildFailResult("Invalid file type");
        String token = request.getHeader("token");
        User user = userService.findByToken(token);
        if(!chatroomService.inChatroom(user, chatroom)){
            throw new UserNotInChatroomException(user.getUserId(), chatroom);
        }
        if(file.getSize() > fileConfig.getMaxFileSize())
            return Result.buildFailResult("File too large");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        // construct message
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setType(type);
        messageDTO.setTimestamp(TimeUtils.timestamp());
        messageDTO.setSenderId(user.getUserId());
        messageDTO.setChatroomId(chatroom);
        // $Date$.$FileSize$.$FileName$
        messageDTO.setContent(format + "." + file.getSize() + "." + file.getOriginalFilename());

        // store file
        if(type == MessageType.FILE)
            fileManager.storeFile(format, file, messageDTO.getTimestamp());
        else if(type == MessageType.IMAGE)
            fileManager.storeImage(file, messageDTO.getTimestamp());

        Message message = messageService.saveMessage(messageDTO);
        simpMessagingTemplate.convertAndSend("/chat/chatroom/" + messageDTO.getChatroomId(), STOMPMessage.buildMessage(STOMPMessageType.BROADCAST_MESSAGE, message));

//        log.info("finish upload " + message.getMessageId());
        return Result.buildSuccessResult("Success to upload file");
    }
    @GetMapping("/api/chat/downloadFile")
    @ResponseBody
    public void downloadFile(@RequestParam("message") Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        log.info(String.valueOf(id));
        Message message = messageService.findById(id);
        String filename = Message.getFileName(message);
        String path = Message.getFilePath(message);
//        log.info(filename + " " + path);
        File file = new File(path);
        if (!file.exists()) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("下载的文件不存在");
            return ;
        }
//        log.info(path);

        InputStream in = new FileInputStream(path);

        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
//        log.info("attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
        response.addHeader("Content-Length", "" + file.length());
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.copy(in, outputStream);
        outputStream.close();
        in.close();
//        log.info("finish download " + id);
//        return response;
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
