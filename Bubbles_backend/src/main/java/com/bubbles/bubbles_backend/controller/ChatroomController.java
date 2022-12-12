package com.bubbles.bubbles_backend.controller;

import com.bubbles.bubbles_backend.dto.PassportDTO;
import com.bubbles.bubbles_backend.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@Slf4j
public class ChatroomController {
    @PostMapping("/api/chatroom/verifyPassport")
    public Result verifyPassport(@RequestBody @Valid PassportDTO passportDTO){
        log.info(passportDTO.getPassport());
        HashMap<String, Object> data = new HashMap<>();
        data.put("max_user", 40);
        return Result.buildSuccessResult("Verify Success", data);
    }
}
