package com.bubbles.bubbles_backend.controller;

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
    @Autowired
    ChatController(SimpMessagingTemplate simpMessagingTemplate, MessageService messageService, UserService userService, ChatroomService chatroomService, FileConfig fileConfig){
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageService = messageService;
        this.userService = userService;
        this.chatroomService = chatroomService;
        this.fileConfig = fileConfig;
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
    public Result uploadFile(@RequestParam("file")MultipartFile file, @RequestParam("chatroom")Integer chatroom, HttpServletRequest request) throws Exception {
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
        messageDTO.setType(MessageType.FILE);
        messageDTO.setTimestamp(TimeUtils.timestamp());
        messageDTO.setSenderId(user.getUserId());
        messageDTO.setChatroomId(chatroom);
        // $Date$.$File Name$
        messageDTO.setContent(format + "." + file.getOriginalFilename());

        // store file
        String realPath = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath();
        //文件夹路径,这里以时间作为目录
        String path = realPath + "static/" + format;
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        file.transferTo(new File(f, file.getOriginalFilename()));

        Message message = messageService.saveMessage(messageDTO);
        simpMessagingTemplate.convertAndSend("/chat/chatroom/" + messageDTO.getChatroomId(), STOMPMessage.buildMessage(STOMPMessageType.BROADCAST_MESSAGE, message));

        return Result.buildSuccessResult("Success to upload file");
    }
    @GetMapping("/api/chat/downloadFile")
    @ResponseBody
    public void downloadFile(@RequestParam("message") Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        log.info(String.valueOf(id));
        Message message = messageService.findById(id);
        String content = message.getContent();
        int split = content.indexOf('.');
        String folder = content.substring(0, split);
        String filename = content.substring(split + 1);

        String realPath = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath();
        String path = realPath + "static/" + folder + "/" + filename;
        File file = new File(path);
        if (!file.exists()) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("下载的文件不存在");
            return ;
        }
//        log.info(path);

        InputStream in = new FileInputStream(path);
        int read;

        byte[] b = new byte[4096];
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition","attachment;filename=" + filename);
        response.addHeader("Content-Length", "" + file.length());
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        while ((read = in.read(b)) > 0) {
            outputStream.write(b);
        }
        outputStream.close();
        in.close();
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
