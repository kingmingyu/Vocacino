package com.example.voca.service;

import com.example.voca.dto.JoinDTO;
import com.example.voca.entity.UserEntity;
import com.example.voca.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO){

        String email = joinDTO.getEmail();

        if(userRepository.findByEmail(email) != null){

            System.out.println("이미 존재하는 이메일입니다.");
            return;
        }

        UserEntity data = new UserEntity();

        data.setUsername(joinDTO.getUsername());
        data.setEmail(joinDTO.getEmail());
        data.setRole("ROLE_USER");
        data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        data.setProviderId(null);

        userRepository.save(data);
    }
}
