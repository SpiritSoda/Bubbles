package com.bubbles.bubbles_backend.controller;

import com.bubbles.bubbles_backend.config.JwtConfig;
import com.bubbles.bubbles_backend.dto.UserDTO;
import com.bubbles.bubbles_backend.entity.User;
import com.bubbles.bubbles_backend.service.UserService;
import com.bubbles.bubbles_backend.utils.JwtUtils;
import com.bubbles.bubbles_backend.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {
    private final JwtConfig jwtConfig;
    private final UserService userService;

    @Autowired
    public LoginController(JwtConfig jwtConfig, UserService userService){
        this.jwtConfig = jwtConfig;
        this.userService = userService;
    }

    @RequestMapping("/api/login")
    public Result login(@RequestBody @Valid UserDTO userDTO) throws Exception{
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setUserId(userService.getUserId(user));

        String token = JwtUtils.sign(user, jwtConfig);

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("uid", user.getUserId());

        log.info("User " + user.toString() + " logged in");
        return Result.buildSuccessResult("Login Success", data);
    }
    @RequestMapping("/api/exist")
    public Result exist(@RequestBody @Valid UserDTO userDTO) throws Exception{
        User user = new User();
        user.setUsername(userDTO.getUsername());
        HashMap<String, Object> data = new HashMap<>();
        if(userService.existUsername(user)){
            data.put("result", true);
            data.put("icon", userService.getUserIcon(user));
            return Result.buildSuccessResult("User Exists", data);
        }
        else{
            data.put("result", false);
            return Result.buildSuccessResult("User Not Exist", data);
        }
    }

    @PostMapping("/api/register")
    public Result register(@RequestBody @Valid UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setIcon(userDTO.getIcon());
        int status = userService.registerUser(user);
        switch (status) {
            case 0:
                return Result.buildSuccessResult("Register Success");
            case 1:
                return Result.buildFailResult("User Exists");
            default:
                return Result.buildFailResult("Unknown Error");
        }
    }
}
