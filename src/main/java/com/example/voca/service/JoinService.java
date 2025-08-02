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

        UserEntity data = new UserEntity();

        data.setUsername(joinDTO.getUsername());
        data.setEmail(joinDTO.getEmail());
        data.setRole("ROLE_USER");
        data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        data.setProviderId(null);

        userRepository.save(data);
    }
}
