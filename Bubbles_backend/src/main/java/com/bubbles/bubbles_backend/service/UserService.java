package com.bubbles.bubbles_backend.service;

import com.bubbles.bubbles_backend.dto.UserDTO;
import com.bubbles.bubbles_backend.entity.User;
import com.bubbles.bubbles_backend.exception.PasswordNotValidException;
import com.bubbles.bubbles_backend.exception.UserNotFoundException;
import com.bubbles.bubbles_backend.repo.UserRepository;
import com.bubbles.bubbles_backend.utils.JwtUtils;
import com.bubbles.bubbles_backend.utils.ValidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Integer registerUser(User user){
        if (userRepository.existsUserByUsername(user.getUsername()))
            return 1;
        userRepository.save(user);
        return 0;
    }

    public int verifyLogin(User user) throws Exception {
        User findResult = userRepository.findUserByUsername(user.getUsername());
        if (findResult == null)
            throw new UserNotFoundException(user.getUsername());
        if (!findResult.getPassword().equals(user.getPassword()))
            throw new PasswordNotValidException();
        return findResult.getUserId();
    }

    public User findByUsername(String username) throws Exception {
        User findResult = userRepository.findUserByUsername(username);
        if (findResult == null)
            throw new UserNotFoundException(username);
        return findResult;
    }

    public User findById(int id) throws Exception{
        User user = userRepository.findUserByUserId(id);
        if(user == null)
            throw new UserNotFoundException(id);
        return user;
    }
    public List<User> getUsersByIds(List<Integer> ids) throws Exception{
        return userRepository.findUserByUserIdIn(ids);
    }

    public Boolean existUsername(String username){
        return userRepository.findUserByUsername(username) != null;
    }

    public User findByToken(String token) throws Exception{
        int id = JwtUtils.getUserId(token);
//        log.info(token);
        User user = findById(id);
        if(user == null)
            throw new UserNotFoundException(id);
        return user;
    }

    public void editUserInfo(User user, UserDTO userDTO){
        if(ValidUtils.isValid(userDTO.getUsername()))
            user.setUsername(userDTO.getUsername());
        if(ValidUtils.isValid(userDTO.getPassword()))
            user.setPassword(userDTO.getPassword());
        if(ValidUtils.isValid(userDTO.getAvatar()))
            user.setAvatar(userDTO.getAvatar());
        userRepository.save(user);
    }
}
