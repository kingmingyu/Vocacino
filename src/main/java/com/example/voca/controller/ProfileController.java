package com.example.voca.controller;

import com.example.voca.dto.JoinDTO;
import com.example.voca.entity.UserEntity;
import com.example.voca.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {

    private final UserRepository userRepository;
    public ProfileController (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public String profileP(){

        return "profile";
    }

    @PostMapping("/profile/update")
    public String usernameUpdate(JoinDTO joinDTO){
        UserEntity userEntity = userRepository.findByEmail(joinDTO.getEmail());
        userEntity.setUsername(joinDTO.getUsername());
        userRepository.save(userEntity);
        return "redirect:/profile";
    }

    @PostMapping("/profile/delete")
    public String userDelete(JoinDTO joinDTO){
        UserEntity userEntity = userRepository.findByEmail(joinDTO.getEmail());
        userRepository.delete(userEntity);
        return "redirect:/login";
    }
}
