package com.example.voca.controller;

import com.example.voca.entity.UserEntity;
import com.example.voca.service.UserContextService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final UserContextService userContextService;

    public GlobalControllerAdvice(UserContextService userContextService){
        this.userContextService = userContextService;
    }

    @ModelAttribute("username")
    public String getUsername(){
        UserEntity currentUser = userContextService.getCurrentUser();

        if(currentUser != null){
            return currentUser.getUsername();
        }
        return "손님";
    }
}
