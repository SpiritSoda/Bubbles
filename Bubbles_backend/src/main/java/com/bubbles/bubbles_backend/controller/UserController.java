package com.bubbles.bubbles_backend.controller;

import com.bubbles.bubbles_backend.config.JwtConfig;
import com.bubbles.bubbles_backend.dto.UserDTO;
import com.bubbles.bubbles_backend.entity.User;
import com.bubbles.bubbles_backend.service.UserService;
import com.bubbles.bubbles_backend.utils.JwtUtils;
import com.bubbles.bubbles_backend.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            data.put("avatar", userService.getUserAvatar(user));
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

    @GetMapping("/api/userinfo")
    public Result getInfo(@RequestParam int id) throws Exception{
        User user = userService.getUserById(id);
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("username", user.getUsername());
        data.put("avatar", user.getAvatar());
        return Result.buildSuccessResult("Success to get user info ...", data);
    }

    @GetMapping("/api/userinfos")
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
}
