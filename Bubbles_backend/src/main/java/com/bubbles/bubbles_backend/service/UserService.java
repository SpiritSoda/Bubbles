package com.bubbles.bubbles_backend.service;

import com.bubbles.bubbles_backend.entity.User;
import com.bubbles.bubbles_backend.exception.PasswordNotValidException;
import com.bubbles.bubbles_backend.exception.UserNotFoundException;
import com.bubbles.bubbles_backend.repo.UserRepository;
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

    public int getUserId(User user) throws Exception {
        User findResult = userRepository.findUserByUsername(user.getUsername());
        if (findResult == null)
            throw new UserNotFoundException(user.getUsername());
        if (!findResult.getPassword().equals(user.getPassword()))
            throw new PasswordNotValidException();
        return findResult.getUserId();
    }

    public int getUserAvatar(User user) throws Exception {
        User findResult = userRepository.findUserByUsername(user.getUsername());
        if (findResult == null)
            throw new UserNotFoundException(user.getUsername());
        return findResult.getAvatar();
    }

    public User getUserById(int id) throws Exception{
        return userRepository.findUserByUserId(id);
    }
    public List<User> getUsersByIds(List<Integer> ids) throws Exception{
        return userRepository.findUserByUserIdIn(ids);
    }

    public Boolean existUsername(User user){
        return userRepository.findUserByUsername(user.getUsername()) != null;
    }

}
