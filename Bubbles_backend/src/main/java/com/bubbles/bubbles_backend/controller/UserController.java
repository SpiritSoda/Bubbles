package com.bubbles.bubbles_backend.controller;

import com.bubbles.bubbles_backend.config.JwtConfig;
import com.bubbles.bubbles_backend.dto.ChatroomDTO;
import com.bubbles.bubbles_backend.dto.UserDTO;
import com.bubbles.bubbles_backend.entity.User;
import com.bubbles.bubbles_backend.exception.UserExistsException;
import com.bubbles.bubbles_backend.exception.UserNotFoundException;
import com.bubbles.bubbles_backend.service.UserService;
import com.bubbles.bubbles_backend.utils.JwtUtils;
import com.bubbles.bubbles_backend.utils.Result;
import com.bubbles.bubbles_backend.utils.ValidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@CrossOrigin
public class UserController {
    private final JwtConfig jwtConfig;
    private final UserService userService;

    @Autowired
    public UserController(JwtConfig jwtConfig, UserService userService){
        this.jwtConfig = jwtConfig;
        this.userService = userService;
    }

    @PostMapping("/api/login")
    public Result login(@RequestBody @Valid UserDTO userDTO) throws Exception{
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setUserId(userService.verifyLogin(user));

        String token = JwtUtils.sign(user, jwtConfig);

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("uid", user.getUserId());

        log.info("User " + user.toString() + " logged in");
        return Result.buildSuccessResult("Login Success", data);
    }
    @PostMapping("/api/exist")
    public Result exist(@RequestBody @Valid UserDTO userDTO) throws Exception{
        HashMap<String, Object> data = new HashMap<>();
        if(userService.existUsername(userDTO.getUsername())){
            User user = userService.findByUsername(userDTO.getUsername());
            data.put("result", true);
            data.put("avatar", user.getAvatar());
            data.put("id", user.getUserId());
            return Result.buildSuccessResult("User Exists", data);
        }
        else{
            throw new UserNotFoundException(userDTO.getUsername());
        }
    }

    @PostMapping("/api/register")
    public Result register(@RequestBody @Valid UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setAvatar(userDTO.getAvatar());
        int status = userService.registerUser(user);
        switch (status) {
            case 0:
                return Result.buildSuccessResult("Register Success");
            case 1:
                return Result.buildFailResult(3, "User Exists");
            default:
                return Result.buildFailResult("Unknown Error");
        }
    }

    @GetMapping("/api/user/userInfo")
    public Result getUserInfoById(@RequestParam @Valid int id) throws Exception{
        User user = userService.findById(id);
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("username", user.getUsername());
        data.put("avatar", user.getAvatar());
        return Result.buildSuccessResult("Success to get user info ...", data);
    }
    @GetMapping("/api/user/selfInfo")
    public Result getUserInfoByToken(HttpServletRequest httpServletRequest) throws Exception{
        String token = httpServletRequest.getHeader("token");
        User user = userService.findByToken(token);
        HashMap<String, Object> data = new HashMap<>();
        List<ChatroomDTO> chatrooms = user.getChatrooms().stream().map(chatroom -> new ChatroomDTO(chatroom)).collect(Collectors.toList());
        data.put("id", user.getUserId());
        data.put("username", user.getUsername());
        data.put("avatar", user.getAvatar());
        data.put("chatrooms", chatrooms);
        return Result.buildSuccessResult("Success to get user info ...", data);
    }

    @GetMapping("/api/user/userInfos")
    public Result getInfos(@RequestParam @Valid List<Integer> ids) throws Exception{
        List<User> users = userService.getUsersByIds(ids);
        HashMap<String, Object> data = new HashMap<>();
        List<HashMap<String, Object>> userInfos = users.stream().map(user -> {
            HashMap<String, Object> info = new HashMap<>();
            info.put("id", user.getUserId());
            info.put("username", user.getUsername());
            info.put("avatar", user.getAvatar());
            return info;
        }).collect(Collectors.toList());
        data.put("users", userInfos);
        return Result.buildSuccessResult("Success to get user info ...", data);
    }
    @PostMapping("/api/user/edit")
    public Result edit(@RequestBody @Valid UserDTO userDTO, HttpServletRequest httpServletRequest) throws Exception {
        String token = httpServletRequest.getHeader("token");
        User user = userService.findByToken(token);
        if(ValidUtils.isValid(userDTO.getUsername())
                && !userDTO.getUsername().equals(user.getUsername())
                && userService.existUsername(userDTO.getUsername()))
            throw new UserExistsException(userDTO.getUsername());
        userService.editUserInfo(user, userDTO);
        return Result.buildSuccessResult("Success to edit user info ...");
    }
}
