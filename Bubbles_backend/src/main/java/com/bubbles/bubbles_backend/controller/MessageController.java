package com.bubbles.bubbles_backend.controller;

import com.bubbles.bubbles_backend.dto.MessageDTO;
import com.bubbles.bubbles_backend.entity.Message;
import com.bubbles.bubbles_backend.service.MessageService;
import com.bubbles.bubbles_backend.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
public class MessageController {
    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    @GetMapping("/api/message/getMessage")
    public Result getMessage(@RequestParam int startId, @RequestParam int cnt){
        List<Message> messages = messageService.getFromIdWithFixedCount(startId, cnt);
        HashMap<String, Object> data = new HashMap<>();
        data.put("count", messages.size());
        data.put("messages", messages);
        return Result.buildSuccessResult("Success to get messages ...", data);
    }
    @PostMapping("/api/message/sendMessage")
    public Result sendMessage(@RequestBody @Valid MessageDTO messageDTO){
        messageService.saveMessage(messageDTO);
        return Result.buildSuccessResult("Success to send message ...");
    }
}
