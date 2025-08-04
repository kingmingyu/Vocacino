package com.example.voca.service;

import com.example.voca.entity.UserEntity;
import com.example.voca.repository.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserContextService {
    private final UserRepository userRepository;

    public UserContextService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserEntity getCurrentUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        System.out.println(email);

        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return null;
        }


        return userRepository.findByEmail(email);
    }
}
