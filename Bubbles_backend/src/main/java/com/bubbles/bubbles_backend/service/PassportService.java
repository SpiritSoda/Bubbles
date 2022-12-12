package com.bubbles.bubbles_backend.service;

import com.bubbles.bubbles_backend.config.JwtConfig;
import com.bubbles.bubbles_backend.entity.Passport;
import com.bubbles.bubbles_backend.repo.PassportRepository;
import com.bubbles.bubbles_backend.utils.ChatroomPassportUtils;
import com.bubbles.bubbles_backend.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PassportService {
    private final PassportRepository passportRepository;
    private final JwtConfig jwtConfig;

    @Autowired
    public PassportService(PassportRepository passportRepository, JwtConfig jwtConfig) {
        this.passportRepository = passportRepository;
        this.jwtConfig = jwtConfig;
    }
    public void save(String token, int type){
        Passport passport = new Passport(token, type);
        passportRepository.save(passport);
    }
    public boolean verify(String token){
        Passport passport = passportRepository.findByToken(token);
        if(passport == null)
            return false;
        try {
            if(passport.getType() == 0)
                ChatroomPassportUtils.verify(token, jwtConfig);
        }
        catch (Exception e){
            passportRepository.delete(passport);
            return false;
        }
        return true;
    }
    public boolean consume(String token){
        if(!verify(token))
            return false;
        Passport passport = passportRepository.findByToken(token);
        passportRepository.delete(passport);
        return true;
    }
}
