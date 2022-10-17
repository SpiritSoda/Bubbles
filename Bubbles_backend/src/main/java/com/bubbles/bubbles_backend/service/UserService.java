package com.bubbles.bubbles_backend.service;

import com.bubbles.bubbles_backend.entity.AnonymousUser;
import com.bubbles.bubbles_backend.repo.AnonymousUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnonymousUserService {
    private AnonymousUserRepository anonymousUserRepository;

    @Autowired
    public AnonymousUserService(AnonymousUserRepository anonymousUserRepository){
        this.anonymousUserRepository = anonymousUserRepository;
    }

    public Integer registerUser(AnonymousUser user){
        return anonymousUserRepository.save(user).getUserId();
    }
}
