package com.example.voca.controller;

import com.example.voca.entity.UserEntity;
import com.example.voca.service.UserContextService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainP(){

        return "main";
    }
}
